// General
function forAll() {
    if (String.prototype.replaceAll === undefined) {
        String.prototype.replaceAll = function (s1,s2) {
            return this.replace(new RegExp(s1,"gm"),s2);
        }
    }
}

function getCookie(cookie_name) {
    let allcookies = document.cookie;
    let cookie_pos = allcookies.indexOf(cookie_name);
    if (cookie_pos !== -1) {
        cookie_pos = cookie_pos + cookie_name.length + 1;
        let cookie_end = allcookies.indexOf(";", cookie_pos);
        if (cookie_end === -1) {
            cookie_end = allcookies.length;
        }
        return unescape(allcookies.substring(cookie_pos, cookie_end));
    }
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*86400000));  // 24*60*60*1000
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

function clearCookie(name) {
    setCookie(name, "", -1);
}

function format_params(params) {
    if (params === null) {
        return '';
    }
    let result = '?';
    for (let key in params) {
        result += key + '=' + params[key] + '&';
    }
    return result.slice(0, -1);
}
function get(url, func, parms=null) {
    let httpRequest = new XMLHttpRequest();
    url += format_params(parms);
    httpRequest.open('GET', url, true);
    httpRequest.send();
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            func(httpRequest)
        } else {
            console.log('Network error');
        }
    };
}

function post_json(url, data, callback, parms) {
    let xhr = new XMLHttpRequest();
    url += format_params(parms);
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            callback(xhr.responseText);
        } else {
            console.log('Network error');
        }
    };
    xhr.send(JSON.stringify(data));
}

function post_form(url, data, callback, parms) {
    let xhr = new XMLHttpRequest();
    url += format_params(parms);
    xhr.open('POST', url, true);
    // xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            callback(xhr.responseText, xhr.status);
        } else {
            console.log('Network error');
        }
    };
    xhr.send(data);
}

function verify_login_status() {
    let cookie = getCookie('TOKEN');
    let username = getCookie('USERNAME');
    if (cookie === undefined || username === undefined) {
        clearCookie('TOKEN');
        clearCookie('USERNAME');
        window.location.href = '/';
    } else {
        post(
            '/logedin',
            {'c': cookie, 'u': username},
            function (httpRequest) {
                let json = JSON.parse(httpRequest.responseText);
                let valid = json['valid'];
                if (valid === 0) {
                    clearCookie('TOKEN');
                    clearCookie('USERNAME');
                    window.location.href = '/';
                }
            }
        )
    }
}

function generate_random(n=8){
    var result = [];
    for(var i=0;i<n;i++){
        var ranNum = Math.ceil(Math.random() * 25);
        result.push(String.fromCharCode(65+ranNum));
    }
    return result.join('');
}

function logout() {
    clearCookie('TOKEN');
    clearCookie('USERNAME');
    window.location.href = '/';
}


function normal_greet() {
    let user = getCookie('USERNAME');
    document.title += ': ' + user + ' - Basket';
    document.getElementById('welcome').innerText = 'Welcome to Basket, ' + user + '.';
}

function remove_file(file_uuid) {
    console.log(file_uuid);
    document.getElementById('file_' + file_uuid).remove();
    delete files[file_uuid];
    if (document.getElementById('uploaded-table').getElementsByTagName('tbody').length === 1) {
        document.getElementById('uploaded-table').remove();
    }
}

var files = {};
function upload() {
    var file_num = 0;
    if (document.getElementById('files').files.length === 0) {
        window.alert('Please select a file.');
        return
    }
    if (document.getElementById('uploaded-table')) {
        let table = document.getElementById('uploaded-table');
        let file_info = document.getElementById('files').files;
        for (let f = 0; f < file_info.length; f++) {
            table.innerHTML += '<tbody id="file_' + file_num + '"><tr><td>' + file_info[f].name + '</td><td>Waiting for upload</td><td><button onclick="">Cancel</button></td></tr></tbody>'
            file_num += 1;
        }
    } else {
        let table = document.createElement('table');
        table.id = 'uploaded-table';
        table.innerHTML = "<th>File Name</th><th>State</th><th>Remove</th>"
        let file_info = document.getElementById('files').files;
        for (let f = 0; f < file_info.length; f++) {
            table.innerHTML += '<tbody id="file_' + file_num + '"><tr><td>' + file_info[f].name + '</td><td>Waiting for upload</td><td><button onclick="">Cancel</button></td></tr></tbody>'
            file_num += 1;
        }
        document.getElementById('upload-section').appendChild(table);
    }
    var body = new FormData();
    for (let i in document.getElementById('files').files) {
        body.append("files", document.getElementById("files").files[i]);
    }
    let httpRequest = new XMLHttpRequest();
    httpRequest.open('POST', '/upload', true);
    httpRequest.send(body);
    httpRequest.onreadystatechange = function () {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            let json = JSON.parse(httpRequest.responseText);
            if (json['success'] !== 0) {
                window.alert('Failed');
            } else {
                let file_info = json['file'];
                for (let i = 0; i < file_num; i ++) {
                    console.log('ppp')
                    let file_tag = document.getElementById('file_'+i);
                    file_tag.id = 'file_' + file_info[i][1];
                    file_tag.getElementsByTagName('td')[1].innerText = 'Uploaded';
                    file_tag.getElementsByTagName('td')[2].innerHTML = '<button onclick="remove_file(\'' + file_info[i][1] + '\')">Remove</button>';
                    files[file_info[i][1]] = file_info[i][0];
                }
                document.getElementById('files').value = '';
            }
        } else {
            console.log('Network error');
        }
    };
}

function toBool(x) {
    return {
        'false': 0,
        'true': 1
    }[x]
}

function isEmptyObject(obj) {
    for (let n in obj) {
        return false;
    }
    return true;
}

function zfill(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}

function formatDate(date) {
    return [[date.getFullYear(), date.getMonth() + 1, date.getDate()].join('.'), [zfill(date.getHours(), 2), zfill(date.getMinutes(), 2)].join(':')].join(' ')
}

function add_file_link_to_html(file_dict, id) {
    if (!isEmptyObject(file_dict)) {
        let innerHTML = '<span>Files:</span><br>';
        let file_links = Array();
        for (let k in file_dict) {
            file_links.push('<a href="/file?uuid=' + k + '">' + file_dict[k] + '</a>');
        }
        innerHTML += file_links.join('<br>')
        document.getElementById(id).innerHTML += innerHTML
    }
}