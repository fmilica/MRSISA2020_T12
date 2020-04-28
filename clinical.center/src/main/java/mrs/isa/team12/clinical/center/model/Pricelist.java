package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Pricelist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToMany(cascade = {ALL}, fetch = LAZY)
	private Set<PricelistItem> pricelistItems;

	public Pricelist() {}

	public Pricelist(Long id, Set<PricelistItem> pricelistItems) {
		super();
		this.id = id;
		this.pricelistItems = pricelistItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<PricelistItem> getPricelistItems() {
		return pricelistItems;
	}

	public void setPricelistItems(Set<PricelistItem> pricelistItems) {
		this.pricelistItems = pricelistItems;
	}
	
}
