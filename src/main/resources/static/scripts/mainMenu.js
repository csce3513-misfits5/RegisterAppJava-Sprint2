document.addEventListener("DOMContentLoaded", function(event) {
	getStartTransactionActionElement().addEventListener(
		"click",
		() => { window.location.assign("/transactionDetails");}); //takes user to transaction page

	getViewProductsActionElement().addEventListener(
		"click",
		() => { window.location.assign("/productListing"); }); //takes user to product listing page

	getCreateEmployeeActionElement().addEventListener(
		"click",
		() => { window.location.assign("/employeeDetail"); }); //takes user to a page to create an employee

	getProductSalesReportActionElement().addEventListener(
		"click",
		() => { displayError("Functionality has not yet been implemented."); });

	getCashierSalesReportActionElement().addEventListener(
		"click",
		() => { displayError("Functionality has not yet been implemented."); });
});

// Getters and setters
function getViewProductsActionElement() {
	return document.getElementById("viewProductsButton");
}

function getCreateEmployeeActionElement() {
	return document.getElementById("createEmployeeButton");
}

function getStartTransactionActionElement() {
	return document.getElementById("startTransactionButton");
}

function getProductSalesReportActionElement() {
	return document.getElementById("productSalesReportButton");
}

function getCashierSalesReportActionElement() {
	return document.getElementById("cashierSalesReportButton");
}
// End getters and setters
