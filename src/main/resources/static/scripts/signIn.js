document.addEventListener("DOMContentLoaded", function(event) {
	const employeeIdEditElement = getEmployeeIdEditElement();
	employeeIdEditElement.focus();
	employeeIdEditElement.select();
});

function validateForm() {
	const employeeIdEditElement = getEmployeeIdEditElement();
	if (isNaN(Number(employeeIdEditElement.value))
		|| (Number(employeeIdEditElement.value) <= 0)) {

		displayError("Please provide a valid employee ID.");

		employeeIdEditElement.focus(); //focuses on the ID element for typing
		employeeIdEditElement.select(); //selects evrything inside the element
		
		return false;
	}

	const passwordEditElement = getPasswordEditElement();
	if ((passwordEditElement.value == null)
		|| (passwordEditElement.value.trim() === "")) {

		displayError("Please provide a valid password. It may not be blank.");

		passwordEditElement.focus(); //focuses on the password element for typing
		passwordEditElement.select(); //selects everything inside the element
		
		return false;
	}

	return true; //password not blank & employee ID is numeric and not blank
}

//Getters and setters
function getPasswordEditElement() {
	return document.getElementById("password");
}

function getEmployeeIdEditElement() {
	return document.getElementById("employeeId");
}
//End getters and setters
