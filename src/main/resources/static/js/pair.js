const modal = document.getElementById('modal');
const form = document.getElementById('form');
const modalSubmitBtn = document.getElementById('modal-submit-btn');
const cryptoExchange = document.getElementById('cryptoExchange');
const cryptoSymbol = document.getElementById('cryptoSymbol');

const showAddModal = () => {
    modal.dataset.formType = 'add';
    modal.style.display = 'block';
    modalSubmitBtn.innerText = '추가';
};

const showEditModal = (pair) => {
    cryptoExchange.value = pair.cryptoExchange;
    cryptoSymbol.value = pair.cryptoSymbol.id;

    modal.dataset.formType = 'edit';
    modal.dataset.symbolId = pair.id;
    modalSubmitBtn.innerText = '수정';
    modal.style.display = 'block';
};

const hideAddModal = () => {
    modal.style.display = 'none';
    cryptoExchange.selectedIndex = 0;
    cryptoSymbol.selectedIndex = 0;
};

form.addEventListener('submit', (event) => {
    event.preventDefault();

    let pair = {};

    pair["cryptoExchange"] = cryptoExchange.value
    pair["cryptoSymbolId"] = cryptoSymbol.value

    if (modal.dataset.formType === 'edit') {
        updateProduct(modal.dataset.symbolId, pair);
    } else {
        createProduct(pair);
    }
});

const createProduct = (pair) => {
    axios.post("/admin/pairs", pair)
        .then((response) => {
            window.location.reload();
        }).catch((error) => {
        console.error(error);
    });
};

const updateProduct = (id, pair) => {
    axios.put(`/admin/pairs/${id}`, pair)
        .then((response) => {
            window.location.reload();
        }).catch((error) => {
        console.error(error);
    });
};

const deleteProduct = (id) => {
    axios.delete(`/admin/pairs/${id}`)
        .then((response) => {
            window.location.reload();
        }).catch((error) => {
        console.error(error);
    });
};
