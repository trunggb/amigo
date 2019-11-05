package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "findAllResident", query = "SELECT r FROM Resident r")
@NamedQuery(name = "findResidentByIdNumber", query = "SELECT r FROM Resident r WHERE r.idenNum = :idenNum")
@Entity
@Table(name = "resident")
@Data
@Builder

public class Resident {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "full_name", nullable = true)
	private String fullName;

	@Column(name = "gender")
	private String gender;
	
	@Column(name = "address")
	private String address;

	@Column(name = "iden_num")
	private String idenNum;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;
}
