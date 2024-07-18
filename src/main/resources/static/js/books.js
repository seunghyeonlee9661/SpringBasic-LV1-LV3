// 강의 목록 불러오기
function searchLoan() {
    let id = $('#searchLoan').val();
    let sort = $('#loanSwitch').is(':checked'); // 스위치가 켜져 있으면 true, 꺼져 있으면 false
    Request('/api/loan', 'GET', {
            'id': id,
            'sort': sort,
        })
        .then(function(response) {
            // 응답 결과를 처리하여 모달 내부에 표시
            let tableBody = $('#loanModal .table tbody');
            tableBody.empty(); // 기존 내용을 지움
            if (response.length > 0) {
                response.forEach(function(loan) {
                    // 날짜값 포맷 변경
                    let loanDate = new Date(loan.loanDate).toLocaleString('en-GB', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit',
                    });
                    // 날짜값 포맷 변경
                    let returnDate = loan.returnDate
                        ? new Date(loan.returnDate).toLocaleString('en-GB', {
                            year: 'numeric',
                            month: '2-digit',
                            day: '2-digit',
                    }) : '';
                    let url = '/books/detail/' + loan.bookid;
                    // 테이블 값 추가
                    let row = `<tr style="font-size: 80%;">
                        <td>${loan.membername}</td>
                        <td>${loan.memberphone}</td>
                        <td><a href=${url}>${loan.bookTitle}</a></td>
                        <td>${loan.bookahthor}</td>
                        <td>${loanDate}</td>
                        <td>${returnDate}</td>
                    </tr>`;
                    tableBody.append(row);
                });
            } else { // 데이터가 하나도 없을 경우
                let row = `<tr><td colspan="5">No booksLoans found.</td></tr>`;
                tableBody.append(row);
            }
        })
        .catch(function(error) {
            console.log(error.responseText);
        });
}

function getBookList(page){
    Request('/api/book', 'GET', { page: page })
        .then(function(response) {
            var html = '';
            for (var i = 0; i < response.content.length; i++) {
                var book = response.content[i];
                html += '<tr>';
                html += '<td>' + book.id + '</td>';
                html += '<td><span class="badge ' + (book.loanable ? 'text-bg-success' : 'text-bg-danger') + '">' + (book.loanable ? '가능' : '불가') + '</span></td>';
                html += '<td><a href="/books/detail/' + book.id + '">' + book.title + '</a></td>';
                html += '<td>' + book.author + '</td>';
                html += '<td>' + book.language + '</td>';
                html += '<td>' + book.publisher + '</td>';
                html += '<td>' + getFormattedDate(book.registDate) + '</td>';
                html += '</tr>';
            }
            document.getElementById('bookTableBody').innerHTML = html;
            setPagination(response);
        })
        .catch(function(error) {
            console.log(error.responseText);
        });
}

function addMember() {
    Request('/api/member', 'POST', {
        'id': $('#id1').val() + $('#id2').val(),
        'name': $('#name').val(),
        'gender': $(":input:radio[name=gender]:checked").val(),
        'phoneNumber': $('#phone1').val() + $('#phone2').val() + $('#phone3').val(),
        'address':$('#address').val()
    })
    .then(function(response) {
        alert(response);
        location.href = '/books';
    })
    .catch(function(response) {
       console.log(error.responseText);
    });
}

function getBook(){
    Request(`/api/book/${book_id}`, 'GET', null)
        .then(function(response) {
            $('#bookTitle').text(response.title);
            $('#bookAuthor').text(response.author);
            $('#bookLanguage').text(response.language);
            $('#bookPublisher').text(response.publisher);
            $('#bookRegistDate').text(getFormattedDate(response.registDate));

            var loanButton = $('#loanButton');
            var loanable = response.loanable;
            loanButton.text(loanable ? '대출하기' : '반납하기');
            loanButton.attr('onclick', loanable ? 'loanBook()' : 'returnBook()'); // loanable에 따라 함수 설정
        })
        .catch(function(error) {
            console.log(error.responseText);
        });
}

function loanBook(){
    var input = prompt("주민등록번호를 입력하세요");
    Request('/api/loan', 'POST', {
        'member_id': input,
        'book_id' : book_id
    })
    .then(function(response) {
        alert(response);
        location.href = location.href;
    })
    .catch(function(error) {
        alert(error.responseText);
    });
}

function returnBook(){
    var input = prompt("주민등록번호를 입력하세요");
    Request('/api/loan', 'PUT', {
        'member_id': input,
        'book_id' : book_id
    })
    .then(function(response) {
        alert(response);
        location.href = location.href;
    })
    .catch(function(error) {
        alert(error.responseText);
    });
}

function addBook(){
    Request('/api/book', 'POST', {
        'title': $('#title').val(),
        'author': $('#author').val(),
        'language': $('#language').val(),
        'publisher': $('#publisher').val()
    })
    .then(function(response) {
        alert('도서가 추가되었습니다.');
        location.href = '/books/detail/' + response;
    })
    .catch(function(error) {
       console.log(error.responseText);
    });

}