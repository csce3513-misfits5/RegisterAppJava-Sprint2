package edu.uark.registerapp.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.CreateTransaction;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@RestController
@RequestMapping(value = "/api/transaction")
public class TransactionRestController extends BaseRestController {
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ApiResponse createProduct(
		@RequestBody final CreateTransaction createTransaction,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		try {
			final ActiveUserEntity activeUserEntity =
				this.validateActiveUserCommand
					.setSessionKey(request.getSession().getId())
					.execute();

			if (activeUserEntity == null) {
				return this.redirectSessionNotActive(response);
			}
		} catch (final UnauthorizedException e) {
			return this.redirectSessionNotActive(response);
		}

		final UUID[] productIds = createTransaction.getProductIds();
		for (int i = 0; i < productIds.length; i++) {
			System.out.println(productIds[i].toString());
		}

		// TODO: Take the array of product IDs and write it to the database as transaction entry and transaction records. (See the example code I provided in Sprint 3.)
		return new ApiResponse();
	}
}
