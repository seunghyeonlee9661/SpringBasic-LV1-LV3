// 강의 목록 불러오기
function getPostList(page) {
    Request('/api/post', 'GET', {
            page: page
        })
        .then(function(response) {
            var html = '';
            for (var i = 0; i < response.content.length; i++) {
                var post = response.content[i];
                html += '<tr>';
                html += '<td>' + post.id + '</td>';
                html += '<td><a href="/posts/detail/' + post.id + '">' + post.title + '</a></td>';
                html += '<td>' + post.writer + '</td>';
                html += '<td>' + post.text + '</td>';
                html += '<td>' + getFormattedDate(post.date) + '</td>';
                html += '</tr>';
            }
            document.querySelector('tbody').innerHTML = html;
            setPagination(response);
        })
        .catch(function(error) {
            console.log(error.responseText);
        });
}

// 게시물 목록 불러오기
function getPost(id) {
    Request(`/api/post/${id}`, 'GET', null)
        .then(function(response) {
            $('#title').text(response.title);
            $('#date').text(response.date);
            $('#writer').text(response.writer);
            $('#text').text(response.text);
        })
        .catch(function(error) {
            console.log(error.responseText);
        });
}

// 게시물 삭제
function deletePost(id){
    var input = prompt("Password");
    Request('/api/post', 'DELETE', {
        'id' : id,
        'password' : input
    })
    .then(function(response) {
        alert('게시물이 삭제되었습니다.');
        window.location.href = '/posts';
    })
    .catch(function(error) {
       console.log(error.responseText);
    });
}

// 게시물 추가
function addPost() {
    Request('/api/post', 'POST', {
        'title': $('#title').val(),
        'writer': $('#writer').val(),
        'password': $('#password').val(),
        'text': $('#text').val()
    })
    .then(function(response) {
        alert('게시물이 추가되었습니다.');
        location.href = '/posts/detail/' + response;
    })
    .catch(function(response) {
       console.log(error.responseText);
    });
}

// 게시판 수정
function editPost(id) {
    var input = prompt("Password");
    Request('/api/post', 'PUT', {
       'id': id,
       'title': $('#title').val(),
       'writer': $('#writer').val(),
       'password': input,
       'text': $('#text').val()
   })
   .then(function(response) {
       alert('게시물이 수정되었습니다.');
       location.href = '/posts/detail/' + id;
   })
   .catch(function(error) {
       alert(error);
       console.log(error.responseText);
   });
}

// 게시판 수정 전 정보
function getEditPost(id){
    Request(`/api/post/${id}`, 'GET', null)
        .then(function(response) {
            $('#title').val(response.title);
            $('#writer').val(response.writer);
            $('#text').val(response.text);
        })
        .catch(function(error) {
            console.log(error.responseText);
        });
}