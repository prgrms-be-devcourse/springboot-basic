var main = {
    init: function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });

        $('#btn-all-delete').on('click', function () {
            _this.deleteAll();
        });
    },
    save: function () {
        var isRight = true;

        if ($('#name').val().trim() == '') {
            alert("이름을 입력하세요.")
            isRight = false;
            return false;
        }

        if (!isRight) {
            return;
        }

        if ($('#value').val().trim() == '') {
            alert("값을 입력하세요.")
            isRight = false;
            return false;
        }

        if (!isRight) {
            return;
        }

        var result = $('#type option:selected').val();
        if (result !== 'FixedAmountVoucher' && result !== 'PercentDiscountVoucher') {
            alert("타입을 선택하세요.")
            isRight = false;
            return false;
        }

        if (!isRight) {
            return;
        }

        $(this).prop("disabled", true);
        $(this).prop("disabled", false);

        var data = {
            name: $('#name').val(),
            value: $('#value').val(),
            type: $('#type').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/voucher/create',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('바우처가 등록되었습니다.');
            window.location.href = '/voucher/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update: function () {
        var isRight = true;

        if ($('#name').val().trim() == '') {
            alert("이름을 입력하세요.")
            isRight = false;
            return false;
        }

        if (!isRight) {
            return;
        }

        if ($('#value').val().trim() == '') {
            alert("값을 입력하세요.")
            isRight = false;
            return false;
        }

        if (!isRight) {
            return;
        }

        $(this).prop("disabled", true);
        $(this).prop("disabled", false);


        var data = {
            id: $('#id').val(),
            name: $('#name').val(),
            value: $('#value').val(),
            type: $('#type').val(),
            customerId: $('#customerId').val()
        };

        $.ajax({
            type: 'PUT',
            url: '/api/voucher/update',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('바우처가 수정되었습니다.');
            window.location.href = '/voucher/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        var id = $('#voucherId').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/voucher/delete/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('바우처가 삭제되었습니다.');
            window.location.href = '/voucher/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    deleteAll: function () {
        $.ajax({
            type: 'DELETE',
            url: '/api/voucher/deleteAll'
        }).done(function () {
            alert('모든 바우처가 삭제되었습니다.');
            window.location.href = '/voucher/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();