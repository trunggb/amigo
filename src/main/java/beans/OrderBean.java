package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.primefaces.PrimeFaces;

import entities.Order;
import entities.OrderDetail;
import entities.OrderStatus;
import entities.Product;
import entities.User;
import lombok.Getter;
import lombok.Setter;
import services.DialogService;
import services.OrderDetailService;
import services.OrderService;
import services.ProductService;

@SuppressWarnings("deprecation")
@ManagedBean(name = "orderBean")
@ViewScoped
public class OrderBean implements Serializable {

	private static final int NUM_PROD = 5;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4098294323352760786L;

	@ManagedProperty(value = "#{userBean}")
	@Setter
	private UserBean userBean;

	@Getter
	@Setter
	private List<Order> orders;

	@Getter
	@Setter
	private User loginUser;
	
	@Getter
	@Setter
	private List<Product> products;

	@Getter
	@Setter
	private List<OrderDetail> orderDetails;

	@Getter
	@Setter
	private List<Product> selectedProducts;

	@Getter
	@Setter
	private Order removedOrder;

	@Getter
	@Setter
	private String note;

	@Getter
	@Setter
	private Integer orderId;

	@Inject
	OrderService orderService;

	@Inject
	ProductService productService;

	@Inject
	DialogService dialogService;

	@Inject
	OrderDetailService orderDetailService;

	Logger logger = Logger.getLogger(ProductService.class);

	@PostConstruct
	public void init() {
		if (Objects.isNull(userBean.getLoginUser())) {
			PrimeFaces.current().executeScript("top.redirectTo('index.xhtml')");
		} else {
			this.loginUser = userBean.getLoginUser();
			this.orders = orderService.findAll();
			this.products = productService.findAll();
			this.orderDetails = new ArrayList<>();
			for (int i = 0; i < NUM_PROD; i++) {
				Product product = Product.builder().id(-1).build();
				OrderDetail orderDetail = OrderDetail.builder().product(product).amount(0).build();
				this.orderDetails.add(orderDetail);
			}
		}

	}

	public void onClickProductButton() {
		PrimeFaces.current().executeScript("top.redirectTo('product.xhtml')");
		logger.debug("Get " + orders.size() + " orders from database");
	}

	public void onClickCreateOrder() {
		// Get data from create dialog
		for (OrderDetail orderDetail : orderDetails) {
			Optional<Product> optionalProduct = productService.find(orderDetail.getProduct().getId());
			if (optionalProduct.isPresent()) {
				Product product = optionalProduct.get();
				int remain = product.getAmount() - orderDetail.getAmount();
				if (remain >= 0 && orderDetail.getAmount() > 0) {
					orderDetail.setProduct(product);
					product.setAmount(remain);
					productService.update(product);
				}
			}
		}

		orderDetails = orderDetails.stream().filter(detail -> Objects.nonNull(detail.getProduct().getName()))
				.collect(Collectors.toList());
		Order order = Order.builder().createdDate(new Date()).user(loginUser).note(note)
				.status(OrderStatus.IN_CREATED).orderDetails(orderDetails).build();

		this.orderService.createOrder(order);
		PrimeFaces.current().executeScript("showSuccessMessage('Order created succesfully!')");
		PrimeFaces.current().executeScript("reloadPage();");
	}

	public void viewOrder(Integer orderId) {
		List<OrderDetail> details = orderDetailService.findDetailsByOrderId(orderId);
		int height = 55 + (details.size() + 1) * 35 + 180;
		Map<String, Object> options = dialogService.createDialogOption(550, height);

		Map<String, List<String>> params = new HashMap<>();
		List<String> productId = new ArrayList<>(); // just send one id
		productId.add(String.valueOf(orderId));
		params.put("orderId", productId);
		PrimeFaces.current().dialog().openDynamic("viewOrder", options, params);
	}
	
	public void updateOrder(Integer orderId) {
		Map<String, Object> options = dialogService.createDialogOption(650, 600);

		Map<String, List<String>> params = new HashMap<>();
		List<String> productId = new ArrayList<>(); // just send one id
		productId.add(String.valueOf(orderId));
		params.put("orderId", productId);
		PrimeFaces.current().dialog().openDynamic("updateOrder", options, params);
	}

	public void onClickRemoveButton(int orderId) {
		Optional<Order> optionalOrder = orderService.find(orderId);
		if (optionalOrder.isPresent()) {
			this.removedOrder = optionalOrder.get();
			PrimeFaces.current().executeScript("PF('remove-order-dialog').show();");
		} else {
			logger.warn("Can not find order with id " + orderId);
		}
	}

	public void removeOrder() {
		orderService.remove(removedOrder);
		PrimeFaces.current().executeScript("showSuccessMessage('Order removed succesfully!')");
		PrimeFaces.current().executeScript("reloadPage()");
	}
	public void onClickLogoutButton() {
		userBean.setLoginUser(null);
		PrimeFaces.current().executeScript("top.redirectTo('index.xhtml')");
	}
}
