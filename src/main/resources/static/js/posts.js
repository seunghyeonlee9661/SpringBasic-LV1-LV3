// 강의 목록 불러오기
function getPosts(page) {
    console.log(page);
    Request('/posts/api/posts', 'GET', {
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
        .catch(function(response) {
            alert('게시물 목록을 불러오는 중 오류가 발생했습니다.');
            console.error(error);
        });
}

// 강사 목록 불러오기
function getPost(id) {
    Request('/posts/api/post', 'GET', {
          id: id,
        })
        .then(function(response) {
            $('#title').text(response.title);
            $('#date').text(response.date);
            $('#writer').text(response.writer);
            $('#text').text(response.text);
        })
        .catch(function(error) {
            alert('게시물 정보를 불러오는 중 오류가 발생했습니다.');
            console.error(error);
        });
}

// 게시물 삭제
function deletePost(id){
    var input = prompt("Password");
    Request('/posts/api/password', 'POST', {
      id: id,
      input: input,
    })
    .then(function(response) {
        alert(response);
        if (confirm('삭제하시겠습니까?')) {
            Request('/posts/api/post?id=' + id, 'DELETE', null)
                .then(function(response) {
                    alert('게시물이 삭제되었습니다.');
                    window.location.href = '/posts';
                })
                .catch(function(error) {
                    alert('게시물을 삭제하는 중 오류가 발생했습니다.');
                    console.error(error);
                });
        }
    })
    .catch(function(error) {
        alert('암호가 올바르지 않습니다.');
    });
}

// 게시물 추가
function addPost() {
    let form = document.getElementById('postForm');
    if (checkValidity(form)) {
        Request('/posts/api/post', 'POST', {
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
                alert(response);
            });
    }
}

// 게시판 수정
function editPost(id) {
    let form = document.getElementById('postForm');
    if (checkValidity(form)) {
        var input = prompt("Password");
        Request('/posts/api/post?id='+id, 'PUT', {
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
           alert(error.responseText);
       });
    }
}

// 게시판 수정 전 정보
function getEditPost(id){
    Request('/posts/api/post', 'GET', {
          id: id,
        })
        .then(function(response) {
            $('#title').val(response.title);
            $('#writer').val(response.writer);
            $('#text').val(response.text);
        })
        .catch(function(error) {
            alert('게시물 정보를 불러오는 중 오류가 발생했습니다.');
            console.error(error);
        });
}