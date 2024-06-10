const modal = document.getElementById('modal');
const form = document.getElementById('form');
const modalSubmitBtn = document.getElementById('modal-submit-btn')
const imgUrl = document.getElementById('imgUrl');
const name = document.getElementById('name');

const showAddModal = () => {
    modal.dataset.formType = 'add';
    modal.style.display = 'block';
    modalSubmitBtn.innerText = '추가';
};

const showEditModal = (symbol) => {
    imgUrl.value = symbol.imgUrl
    name.value = symbol.name

    modal.dataset.formType = 'edit';
    modalSubmitBtn.innerText = '수정';
    modal.dataset.symbolId = symbol.id;
    modal.style.display = 'block';
};

const hideAddModal = () => {
    modal.style.display = 'none';
    const elements = modal.getElementsByTagName('input');
    imgUrl.value = ''
    name.value = ''
}

form.addEventListener('submit', (event) => {
    event.preventDefault();

    let symbol = {};

    symbol["imgUrl"] = imgUrl.value
    symbol["name"] = name.value

    if (modal.dataset.formType === 'edit') {
        updateProduct(modal.dataset.symbolId, symbol);
        return;
    }

    createProduct(symbol);
});

const createProduct = (symbol) => {
    axios.post("/admin/symbols", symbol)
        .then((response) => {
            window.location.reload();
        }).catch((error) => {
        console.error(error);
    });
};

const updateProduct = (id, symbol) => {
    axios.put(`/admin/symbols/${id}`, symbol)
        .then((response) => {
            window.location.reload();
        }).catch((error) => {
        console.error(error);
    });
};

const deleteProduct = (id) => {
    axios.delete(`/admin/symbols/${id}`)
        .then((response) => {
            window.location.reload();
        }).catch((error) => {
        console.error(error);
    });
};
