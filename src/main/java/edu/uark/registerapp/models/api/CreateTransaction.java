package edu.uark.registerapp.models.api;

import java.util.UUID;

public class CreateTransaction {
	private UUID[] productIds;
	public UUID[] getProductIds() {
		return this.productIds;
	}
	public CreateTransaction setProductIds(UUID[] productIds) {
		this.productIds = productIds;
		return this;
	}

	public CreateTransaction() {
		this.productIds = new UUID[0];
	}
}