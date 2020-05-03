package mrs.isa.team12.clinical.center.service;

import org.springframework.stereotype.Service;

import mrs.isa.team12.clinical.center.model.PricelistItem;
import mrs.isa.team12.clinical.center.repository.PriceListItemRepository;
import mrs.isa.team12.clinical.center.service.interfaces.PriceListItemService;

@Service
public class PriceListItemImpl implements PriceListItemService {

	private PriceListItemRepository priceRep;
	
	@Override
	public PricelistItem save(PricelistItem pi) {
		return priceRep.save(pi);
	}

}
