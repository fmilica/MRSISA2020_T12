package mrs.isa.team12.clinical.center.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pricelist")
public class Pricelist {
	/*Koliko vidim price list nam trenutno nije vezan ni za klinicki centar ni za kliniku, mada mozda ni ne treba da bude vezan
	 * jer cemo podrazumevati da je vezan za klinicki centar sto da ne, pravimo ga na pocetku kada i klinicki centar*/
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@OneToOne(fetch = LAZY)
	@JoinColumn(name = "clinical_centre_id", referencedColumnName = "id", nullable = false)
	private ClinicalCentre clinicalCentre;
	
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
