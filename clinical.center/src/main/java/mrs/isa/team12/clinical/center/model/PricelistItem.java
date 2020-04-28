package mrs.isa.team12.clinical.center.model;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class PricelistItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name = "price", unique = false, nullable = false )
	private Double price;
	
	@Column(name = "start_date", unique = false, nullable = false )
	private Date startDate;
	
	@Column(name = "end_date", unique = false, nullable = false )
	private Date endDate;
	
	@ManyToOne
	@JoinColumn(name = "pricelist_id", referencedColumnName = "id", nullable = true)
	private Pricelist pricelist;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "appointment_type_id", referencedColumnName = "id", nullable = true)
	private AppointmentType appointmentType;
	
	public PricelistItem() {}

	public PricelistItem(Long id, Double price, Date startDate, Date endDate, Pricelist pricelist,
			AppointmentType appointmentType) {
		super();
		this.id = id;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pricelist = pricelist;
		this.appointmentType = appointmentType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Pricelist getPricelist() {
		return pricelist;
	}

	public void setPricelist(Pricelist pricelist) {
		this.pricelist = pricelist;
	}

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}
}
