const toastEl = document.querySelector('.toast');
let toast = new bootstrap.Toast(toastEl);

const showToast = (message, type = 'bg-danger') => {
    const toastBody = document.getElementById('toast-body');
    toastEl.classList.remove('bg-danger', 'bg-success', 'bg-warning');
    toastEl.classList.add(type);
    toastBody.innerText = message;
    toast.show();
}

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
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        showToast('사용자 정보가 없습니다.', 'bg-danger');
        return;
    }

    axios.request({
        method: 'post',
        url: '/admin/symbols',
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        data: symbol
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        handleError(error);
    });
};

const updateProduct = (id, symbol) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        showToast('사용자 정보가 없습니다.', 'bg-danger');
        return;
    }

    axios.request({
        method: 'put',
        url: `/admin/symbols/${id}`,
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        data: symbol
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        handleError(error);
    });
};

const deleteProduct = (id) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        showToast('사용자 정보가 없습니다.', 'bg-danger');
        return;
    }

    axios.request({
        method: 'delete',
        url: `/admin/symbols/${id}`,
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        handleError(error);
    });
};

const handleError = (error) => {
    console.error(error);
    if (error.response) {
        const errorMessage = error.response.data.message;
        showToast(errorMessage, 'bg-danger');
    }
};
