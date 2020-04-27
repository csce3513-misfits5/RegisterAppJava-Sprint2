package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.products.ProductQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import edu.uark.registerapp.commands.products.ProductUpdateCommand;

@Controller
@RequestMapping(value = "/shoppingCart")
public class TransactionRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request)
	{
		final Optional<ActiveUserEntity> activeUserEntity =	this.getCurrentUser(request);
		if (!activeUserEntity.isPresent()) 
		{
			return this.buildInvalidSessionResponse();
		} 
		else if (!this.isElevatedUser(activeUserEntity.get())) 
		{
			return this.buildNoPermissionsResponse(ViewNames.PRODUCT_LISTING.getRoute());
		}

		// create modelandview to get product and find count status
		final ModelAndView modelAndView = this.setErrorMessageFromQueryString(new ModelAndView(ViewNames.PRODUCT_DETAIL.getViewName()),	queryParameters);
		int count = this.productUpdateCommand.getApiProduct().getCount();

		// If nothing exists set count to 1 or increment count
		if(count <= 0)
		{
			modelAndView.addObject(ViewModelNames.PRODUCT.getValue(),(new Product()).setLookupCode(StringUtils.EMPTY).setCount(1));
		}
		else
		{
			modelAndView.addObject(ViewModelNames.PRODUCT.getValue(),(new Product()).setCount(count + 1));
		}

		return modelAndView;
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
	public ModelAndView startWithProduct(@PathVariable final UUID productId, @RequestParam final Map<String, String> queryParameters,final HttpServletRequest request) 
	{
		final Optional<ActiveUserEntity> activeUserEntity =	this.getCurrentUser(request);
		if (!activeUserEntity.isPresent()) 
		{
			return this.buildInvalidSessionResponse();
		} 
		else if (!this.isElevatedUser(activeUserEntity.get())) 
		{
			return this.buildNoPermissionsResponse(ViewNames.PRODUCT_LISTING.getRoute());
		}

		final ModelAndView modelAndView =this.setErrorMessageFromQueryString(new ModelAndView(ViewNames.PRODUCT_DETAIL.getViewName()), queryParameters);
		int count = this.productUpdateCommand.getApiProduct().getCount();
		
		// Create and set value to set the count in the try/catch
		int countReplace;
		if(count <= 0)
		{
			countReplace = 1;
		}
		else
		{
			countReplace = count + 1;
		}

		try 
		{
			modelAndView.addObject(ViewModelNames.PRODUCT.getValue(), this.productQuery.setProductId(productId).execute());
		} 
		catch (final Exception e) 
		{
			modelAndView.addObject(ViewModelNames.ERROR_MESSAGE.getValue(),	e.getMessage());
			modelAndView.addObject(ViewModelNames.PRODUCT.getValue(),(new Product()).setCount(countReplace).setLookupCode(StringUtils.EMPTY));
		}

		return modelAndView;
	}



	// Properties
	@Autowired
	private ProductQuery productQuery;
	@Autowired
	private ProductUpdateCommand productUpdateCommand;
}
