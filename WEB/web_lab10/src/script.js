const dom = (function () {

    let formId = "";

    const allToursFrom = {
        submitButton: {
            type: 'submit',
            class: 'btn btn-primary',
            value: 'Вывести туры'
        }
    };

    const ordersByClientFrom = {
        clientId: {
            label: 'Номер клиента',
            type: 'number',
            class: 'form-control',
            placeholder: 'Введите номер клиента',
            name: 'clientId',
            minValue: '1',
            step: '1',
            id: 'clientId-input',
        },
        submitButton: {
            type: 'submit',
            class: 'btn btn-primary',
            value: 'Подтвердить'
        }
    };

    const burningToursByTypeFrom = {
        typeId: {
            label: 'Тип туров',
            type: 'number',
            class: 'form-control',
            placeholder: 'Введите номер типа туров',
            name: 'typeId',
            id: 'typeId-input',
        },
        submitButton: {
            type: 'submit',
            class: 'btn btn-primary',
            value: 'Подтвердить'
        }
    };

    const makeOrderAndSetDiscountFrom = {
        clientId: {
            label: 'Номер клиента',
            type: 'number',
            class: 'form-control',
            placeholder: 'Введите номер клиента',
            name: 'clientId',
            minValue: '1',
            step: '1',
            id: 'clientId-input',
        },
        tourId: {
            label: 'Номер тура',
            type: 'number',
            class: 'form-control',
            placeholder: 'Введите номер тура',
            name: 'tourId',
            minValue: '1',
            step: '1',
            id: 'tourId-input',
        },
        ordersAmount: {
            label: 'Кол-во заказанных туров',
            type: 'number',
            class: 'form-control',
            placeholder: 'Введите кол-во туров',
            name: 'ordersAmount',
            minValue: '0',
            step: '1',
            id: 'ordersAmount-input'
        },
        discount: {
            label: 'Размер скидки',
            type: 'number',
            class: 'form-control',
            placeholder: 'Введите размер скидки',
            name: 'discount',
            minValue: '0',
            maxValue: '100',
            step: '1',
            id: 'discount-input'
        },
        submitButton: {
            type: 'submit',
            class: 'btn btn-primary',
            value: 'Подтвердить'
        }
    };

    function buildForm(form, type) {
        var array;
        switch (type) {
            case 'allToursForm':
                array = allToursFrom;
                break;
            case 'ordersByClientForm':
                array = ordersByClientFrom;
                break;
            case 'burningToursByTypeForm':
                array = burningToursByTypeFrom;
                break;
            case 'makeOrderAndSetDiscountForm':
                array = makeOrderAndSetDiscountFrom;
                break;
        }

        for(const prop in array) {
            switch (array[prop].type) {
                case "submit":
                    const submit = document.createElement('input');
                    submit.setAttribute('type', array[prop].type);
                    submit.setAttribute('class', array[prop].class);
                    submit.value = array[prop].value;

                    form.appendChild(submit);
                    form.onSubmit = onSubmit();
                    break;

                default:
                    const formDiv = document.createElement('div');
                    formDiv.setAttribute('class', 'form-group');

                    const label = document.createElement('label');
                    label.innerHTML = array[prop].label;
                    formDiv.appendChild(label);

                    const input = document.createElement('input');
                    input.setAttribute('type', array[prop].type);
                    input.setAttribute('class', array[prop].class);
                    input.setAttribute('placeholder', array[prop].placeholder);
                    input.setAttribute('name', array[prop].name);

                    if(array[prop].type === "makeOrderAndSetDiscountForm") {
                        input.setAttribute('max', array[prop].maxValue);
                    }
                    input.setAttribute('min', array[prop].minValue);
                    input.setAttribute('step', array[prop].step);
                    input.setAttribute("id", array[prop].id);
                    input.required = true;
                    formDiv.appendChild(input);

                    form.appendChild(formDiv);
                    break;
            }
        }
    }

    function initPage() {

        const allToursForm = document.getElementById('all-tours-form');
        if (allToursForm != null) {
            buildForm(allToursForm, 'allToursForm');
            console.log('allToursForm');
            formId = 'all-tours-form';
            return
        }

        const ordersByClientForm = document.getElementById('orders-by-client-form');
        if (ordersByClientForm != null) {
            buildForm(ordersByClientForm, 'ordersByClientForm');
            console.log('ordersByClientForm');
            formId = 'orders-by-client-form';
            return
        }

        const burningToursByTypeForm = document.getElementById('burning-tours-by-type-form');
        if (burningToursByTypeForm != null) {
            buildForm(burningToursByTypeForm, 'burningToursByTypeForm');
            console.log('burningToursByTypeForm');
            formId = 'burning-tours-by-type-form';
            return
        }

        const makeOrderAndSetDiscountForm = document.getElementById('make-order-and-set-discount-form');
        if (makeOrderAndSetDiscountForm != null) {
            buildForm(makeOrderAndSetDiscountForm, 'makeOrderAndSetDiscountForm');
            console.log('makeOrderAndSetDiscountForm');
            formId = 'make-order-and-set-discount-form';
            return
        }

    }

    function onSubmit() {}

    return {
        initPage,
        onSubmit
    }

}());

dom.initPage();