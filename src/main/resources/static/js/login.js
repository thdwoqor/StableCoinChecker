const toastEl = document.querySelector('.toast');
let toast = new bootstrap.Toast(toastEl);

const showToast = (message, type = 'bg-danger') => {
    const toastBody = document.getElementById('toast-body');
    toastEl.classList.remove('bg-danger', 'bg-success', 'bg-warning');
    toastEl.classList.add(type);
    toastBody.innerText = message;
    toast.show();
}

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('loginForm').addEventListener('submit', function (event) {
        event.preventDefault();

        const username = document.getElementById('floatingUsername').value;
        const password = document.getElementById('floatingPassword').value;

        const credentials = `${username}:${password}`;

        axios.request({
            method: 'post',
            url: '/admin/login',
            headers: {
                'Authorization': `Basic ${btoa(credentials)}`
            }
        }).then((response) => {
            localStorage.setItem('credentials', btoa(credentials));
            showToast("로그인 성공", 'bg-success');
        }).catch((error) => {
            handleError(error);
        });
    });
});

const handleError = (error) => {
    console.error(error);
    if (error.response) {
        const errorMessage = error.response.data.message;
        showToast(errorMessage, 'bg-danger');
    }
};
