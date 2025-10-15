document.addEventListener("DOMContentLoaded", () => {

  // Mapování názvů fragmentů popupů
  const popupMap = {
    "add-money": "add-money",
    "edit-user": "edit-user",
    "delete-user": "delete-user",
    "register": "register",
    "login": "login",
    "add-product": "add-product",
    "edit-product": "edit-product",
    "delete-product": "delete-product",
    "payment-confirm": "payment-confirm",
    "delete-cart": "delete-cart"
  };

  // --- Otevření popupu ---
  window.openPopup = function (contentId) {
    const overlay = document.getElementById("popup-overlay");
    const body = document.getElementById("popup-body");

    const fragment = popupMap[contentId] || contentId;

    fetch(`/popup/${fragment}`)
      .then(response => {
        if (!response.ok) throw new Error(`Chyba: ${response.status}`);
        return response.text();
      })
      .then(html => {
        body.innerHTML = html;
        overlay.style.display = "flex";
      })
      .catch(err => console.error("Chyba při načítání popup obsahu:", err));
  };

  // --- Zavření popupu ---
  window.closePopup = function () {
    const overlay = document.getElementById("popup-overlay");
    const body = document.getElementById("popup-body");
    if (overlay && body) {
      overlay.style.display = "none";
      body.innerHTML = "";
    }
  };

  // Kliknutí mimo popup zavře okno
  document.addEventListener("click", e => {
    if (e.target.id === "popup-overlay") closePopup();
  });

  // Zavření pomocí klávesy ESC
  document.addEventListener("keydown", e => {
    if (e.key === "Escape") closePopup();
  });
});

// Otevři payment popup s daty
function openPaymentPopup(totalPrice, userBalance) {
    window.openPopup('payment-confirm');

    setTimeout(() => {
        const totalElement = document.getElementById('payment-total');
        const balanceElement = document.getElementById('user-balance');

        if (totalElement) totalElement.textContent = totalPrice;
        if (balanceElement) balanceElement.textContent = userBalance;
    }, 100);
}

// Otevři add product popup
function openAddProductPopup() {
    window.openPopup('add-product');
    setTimeout(() => {
        const form = document.getElementById('add-product');
        if (form) form.reset();
    }, 100);
}

// Otevři edit product popup s daty
function openEditProductPopup(id, name, price, stock, discount) {
    window.openPopup('edit-product');
    setTimeout(() => {
        document.getElementById('edit-product-id').value = id;
        document.getElementById('edit-product-name').value = name;
        document.getElementById('edit-product-price').value = price;
        document.getElementById('edit-product-stock').value = stock;
        document.getElementById('edit-product-discount').value = discount;
        document.getElementById('edit-product').action = '/product/edit/' + id;
    }, 100);
}

// Otevři delete product popup
function openDeleteProductPopup(id) {
    window.openPopup('delete-product');
    setTimeout(() => {
        document.getElementById('delete-product-id').value = id;
        document.getElementById('delete-product').action = '/product/delete/' + id;
    }, 100);
}

// Otevři delete cart popup
function openDeleteCartPopup() {
    window.openPopup('delete-cart');
}