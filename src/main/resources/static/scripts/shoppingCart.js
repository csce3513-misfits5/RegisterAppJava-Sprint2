function findClickedListItemElement(clickedTarget) {
	if (clickedTarget.tagName.toLowerCase() === "li") {
		return clickedTarget;
	} else {
		let ancestorIsListItem = false;
		let ancestorElement = clickedTarget.parentElement;

		while (!ancestorIsListItem && (ancestorElement != null)) {
			ancestorIsListItem = (ancestorElement.tagName.toLowerCase() === "li");

			if (!ancestorIsListItem) {
				ancestorElement = ancestorElement.parentElement;
			}
		}

		return (ancestorIsListItem ? ancestorElement : null);
	}
}

function onCartItemClicked(event) {
	if ((event.target.tagName.toLowerCase() === "input")
		&& (event.target.name === "productQuantity")) {

		return;
	}
	const listItem = findClickedListItemElement(event.target);

	listItem.parentElement.removeChild(listItem);
}

function productClick(event) {
	let i = 0;
	const listItem = findClickedListItemElement(event.target);
	const cartProductsElement = document.getElementById("cartProducts");
	const existingCartProductElements = cartProductsElement.querySelectorAll("li");
	const clickedProductId = listItem.querySelector("input[name='productId']").value;

	for (; i < existingCartProductElements.length; i++) {
		const existingCartProductId = existingCartProductElements[i].querySelector("input[name='productId']").value;

		if (clickedProductId !== existingCartProductId) {
			continue;
		}

        //Set product quantity and multiply price by quantity
        const productQuantityElement = existingCartProductElements[i].querySelector("input[name='productQuantity']");
        const productPriceElement = existingCartProductElements[i].querySelector("input[name='productQuantity'}");
        productPriceElement.value = (Number(productQuantityElement.value * productPriceElement.value)).toString();
        productQuantityElement.value = (Number(productQuantityElement.value) + 1).toString();
     

		return;ß
	}

	const listItemElement = document.createElement("li");
	listItemElement.addEventListener("click", onCartItemClicked);
	cartProductsElement.appendChild(listItemElement);

	const productIdElement = document.createElement("input");
	productIdElement.type = "hidden";
	productIdElement.name = "productId";
	productIdElement.value = listItem.querySelector("input[name='productId']").value;
	listItemElement.appendChild(productIdElement);

	const productLookupcodeElement = document.createElement("span");
    productLookupcodeElement.classList.add("productLookupCodeDisplay");
    productLookupcodeElement.classList.add("productPriceDisplay");
    productLookupcodeElement.innerHTML = listItem.querySelector("span.productLookupCodeDisplay").innerHTML;
    
	listItemElement.appendChild(productLookupcodeElement);

	listItemElement.appendChild(document.createElement("br"));
	listItemElement.appendChild(document.createTextNode("\u00A0\u00A0"));

	const quantityElement = document.createElement("input");
	quantityElement.type = "number";
	quantityElement.name = "productQuantity";
	quantityElement.value = "1";
	quantityElement.classList.add("quantityUpdate");
    listItemElement.appendChild(quantityElement);

    //const priceElement = document.createElement("")
}

function onProductSearchKeyPress(event) {
	if (event.which !== 13) { // ENTER/RETURN
		return;
	}

	const productListElements = document.getElementById("productsListing").children;

	for (let i = 0; i < productListElements.length; i++) {
		const lookupCode = productListElements[i]
			.querySelector("span.productLookupCodeDisplay")
			.innerHTML;

		if (lookupCode.toLowerCase().indexOf(event.target.value.toLowerCase()) >= 0) {
			if (productListElements[i].classList.contains("hidden")) {
				productListElements[i].classList.remove("hidden");
			}
		} else {
			if (!productListElements[i].classList.contains("hidden")) {
				productListElements[i].classList.add("hidden");
			}
		}
	}
}

function onCancelCartClicked() {
	document.getElementById("cartProducts")
		.innerHTML = "";
}

function onCompleteCartClicked() {
	const productIds = [];
	const productIdEditElements = document.getElementById("cartProducts")
		.querySelectorAll("li input[name='productId']");

	for(let i = 0; i < productIdEditElements.length; i++) {
		productIds.push(productIdEditElements[i].value);
	}

	ajaxPost(
		"/api/transaction/",
		{ productIds: productIds },
		(callbackResponse) => {
			if (isErrorResponse(callbackResponse)) {
				return;
			}

			window.location.replace("/mainMenu");
		});
}

document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("productSearch").addEventListener("keypress", onProductSearchKeyPress);

	const productListElements = document.getElementById("productsListing").children;

	for (let i = 0; i < productListElements.length; i++) {
		productListElements[i].addEventListener("click", productClick);
	}

	document.getElementById("cancelCartAction").addEventListener("click", onCancelCartClicked);

	document.getElementById("completeCartAction").addEventListener("click", onCompleteCartClicked);
});
