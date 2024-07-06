function maxLengthCheck(object) {
    if (object.value.length > object.maxLength) {
        object.value = object.value.slice(0, object.maxLength); // 최대 길이를 초과한 부분을 잘라냅니다.
    } else if (!/^\d+$/.test(object.value)) {
        object.value = object.value.replace(/[^\d]/g, ''); // 숫자 이외의 입력을 제거합니다.
    }
}

function checkRole(){
     const token = Cookies.get('Authorization'); // JWT가 저장된 쿠키의 이름을 넣으세요
     if (token) {
         try {
             const decoded = jwt_decode(token);
             if(decoded.auth === "MANAGER"){
                 return true;
             }else{
                 alert('MANAGER 권한이 필요합니다.');
                 return false;
             }
         } catch (error) {
             alert('JWT decoding Error');
             location.href = '/backoffice';
         }
     } else {
         alert('로그인이 필요합니다.');
         location.href = '/backoffice';
     }
 }