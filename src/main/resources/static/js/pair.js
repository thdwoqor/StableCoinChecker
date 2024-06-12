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

    pair["cryptoExchange"] = cryptoExchange.value;
    pair["cryptoSymbolId"] = cryptoSymbol.value;

    if (modal.dataset.formType === 'edit') {
        updateProduct(modal.dataset.symbolId, pair);
    } else {
        createProduct(pair);
    }
});

const createProduct = (pair) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        showToast('사용자 정보가 없습니다.', 'bg-danger');
        return;
    }

    axios.request({
        method: 'post',
        url: '/admin/pairs',
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        data: pair
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        handleError(error);
    });
};

const updateProduct = (id, pair) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        showToast('사용자 정보가 없습니다.', 'bg-danger');
        return;
    }

    axios.request({
        method: 'put',
        url: `/admin/pairs/${id}`,
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        data: pair
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
        url: `/admin/pairs/${id}`,
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
