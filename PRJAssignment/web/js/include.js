/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function saveSearchHistoryToServer(path) {
    // Gửi path đã encode lên server (server sẽ decode lại)
    fetch(contextPath + '/saveSearchHistory', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: 'path=' + encodeURIComponent(path)
    });
}

function saveSearchHistory(path) {
    console.log(path);
    // Chuẩn hóa path nếu cần
    var contextPath = window.contextPath || '';
    if (typeof contextPath === "undefined") contextPath = '';
    if (path.startsWith(contextPath)) {
        path = path.substring(contextPath.length);
    }
    while (path.startsWith("//")) {
        path = path.substring(1);
    }
    if (!path.startsWith("/")) {
        path = "/" + path;
    }

    const name = "searchHistory";
    let arr = [];

    // Lấy giá trị cookie hiện tại, tách theo ký tự phân tách "|"
    const cookieValue = "; " + document.cookie;
    const parts = cookieValue.split("; " + name + "=");
    if (parts.length === 2) {
        arr = parts.pop().split(";").shift().split("|")
            .map(item => item.trim())
            .filter(item => item !== "");
    }

    // Xóa nếu path đã tồn tại (so sánh trực tiếp vì đều là dạng encode)
    arr = arr.filter(item => item !== path);

    // Thêm path mới lên đầu
    arr.unshift(path);

    // Giới hạn tối đa 10 phần tử
    if (arr.length > 10) arr = arr.slice(0, 10);

    // Lưu lại cookie, không encode nữa
    document.cookie = name + "=" + arr.join("|") + ";path=/;max-age=" + (60 * 60 * 24 * 30);
}






function saveViewedCar(globalKey) {
    const name = "viewedCars";
    const value = "; " + document.cookie;
    const parts = value.split("; " + name + "=");
    let arr = [];
    if (parts.length === 2) {
        arr = decodeURIComponent(parts.pop().split(";").shift()).split(",");
    }
    arr = arr.filter(item => item !== globalKey);
    arr.unshift(globalKey);
    if (arr.length > 20)
        arr = arr.slice(0, 20);
    document.cookie = name + "=" + encodeURIComponent(arr.join(",")) + ";path=/;max-age=" + (60 * 60 * 24 * 30);
}


function saveViewedCarToServer(globalKey) {
    if (!globalKey)
        return;

    fetch(contextPath +'/saveViewedCars', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'globalKey=' + encodeURIComponent(globalKey)
    });
}

// Đồng bộ trạng thái "yêu thích" từ cookie khi chưa đăng nhập
function syncFavoriteFromCookie() {
    function getCookie(name) {
        const value = "; " + document.cookie;
        const parts = value.split("; " + name + "=");
        if (parts.length === 2)
            return decodeURIComponent(parts.pop().split(";").shift());
        return "";
    }
    const favoriteStr = getCookie("favoriteCars");
    const favoriteArr = favoriteStr ? favoriteStr.split(",") : [];
    document.querySelectorAll('.favorite-btn[data-globalkey]').forEach(function (btn) {
        const key = btn.getAttribute('data-globalkey');
        if (favoriteArr.includes(key)) {
            btn.classList.add('favorited');
        } else {
            btn.classList.remove('favorited');
        }
    });
}



