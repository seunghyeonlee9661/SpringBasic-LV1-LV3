# 📚 SpringProject

## 🎯 프로젝트 목표
Spring 기초 학습 및 개인 능력 향상을 위한 프로젝트입니다. 주요 목표는 기초적인 게시판 제작과 데이터 관리, 사용자 접근 등을 배우는 것입니다.
- **Spring Framework**와 **Spring Boot**의 기초 개념 이해
- **CRUD** 기능 구현을 통한 데이터 관리
- **사용자 인증 및 권한 관리**
- **프로젝트 설계 및 팀 협업 방법론**
- **개인 능력 향상**을 위한 실습 및 경험 축적

## 📅 프로젝트 단계

### 1️⃣ LV1 (2024년 6월 28일 ~ 7월 1일)
- **목표**: 게시물 작성과 조회, 수정과 삭제와 같은 기본적인 CRUD 기능 구현입니다.
- **주요 기능**:
  - 게시글 작성
  - 게시글 조회
  - 게시글 수정
  - 게시글 삭제
- [프로젝트 Notion 링크](https://leather-pixie-4bc.notion.site/Spring-LV1-d50c9e598ee14c51b5efc0a5e74de0a8)

### 2️⃣ LV2 (2024년 7월 2일 ~ 7월 4일)
- **목표**: 도서와 회원에 대한 CRUD, 관계형 데이터베이스와 대출 기능 등을 통한 JPA에 대한 이해입니다.
- **주요 기능**:
  - 회원 등록
  - 도서 조회
  - 도서 대출 및 반납
  - 회원 대출 내역 조회
- [프로젝트 Notion 링크](https://leather-pixie-4bc.notion.site/Spring-LV2-383a6ae181f94442bd4d69afda41ba71?pvs=4)

### 3️⃣ LV3 (2024년 7월 5일 ~ 7월 8일)
- **목표**: 로그인 회원가입을 통한 JWT 인증 방식, JPA의 순환 참조 해결 등을 학습했습니다.
- **주요 기능**:
  - JWT 기반 회원 가입과 로그인
  - 강사 정보 등록 및 관리
  - 강의 정보 등록 및 관리
  - 강사와 강의 정보 조회
- [프로젝트 Notion 링크](https://leather-pixie-4bc.notion.site/Spring-LV3-7e5de2ef007e4d1bb7c44536e351929c)

### 4️⃣ LV4 & LV5 (2024년 7월 12일 ~ 7월 18일)
- [GitHub Repository](https://github.com/seunghyeonlee9661/Sparta)

## 📚 주요 학습 내용

- **프로젝트 파일과 메소드 네이밍**
  - 자바 파일은 대소문자, 디렉토리는 소문자로 통일했습니다.
  - 메소드 이름을 `find(검색)`, `create(생성)`, `update(수정)`, `remove(삭제)` 기준으로 통일했습니다.

- **어노테이션에 대한 이해와 불필요한 어노테이션 제거**
  - **`@Builder`**: 클래스나 생성자에 붙여 빌더 패턴을 자동 생성합니다. Setter 사용을 지양하고 생성자에서 필드를 안전하게 설정할 수 있도록 지원합니다.
    ```java
    User build = new User.Builder()
              .email(email)
              .password(password)
              .name(name)
              .build();
    ```
  - **`@AllArgsConstructor`**: 모든 매개변수를 받는 생성자를 생성합니다.
  - **`@NoArgsConstructor`**: 매개변수 없는 디폴트 생성자를 생성합니다.
  - **`@RequiredArgsConstructor`**: `final`이나 `@NonNull`인 필드만 매개변수로 받는 생성자를 생성합니다.

- **Setter 사용의 지양**
  - Setter는 객체의 변경 범위를 넓히고 일관성 유지가 어려워집니다.
  - Setter 대신 의도를 파악할 수 있는 메소드 사용을 권장합니다.
    ```java
    @Getter
    @Entity
    @NoArgsConstructor
    public class Teacher {
        ...
        // Set을 대신해 기존의 값을 수정하는 메소드 활용!
        public void update(TeacherRequestDTO requestDto) {
            this.name = requestDto.getName();
            this.year = requestDto.getYear();
            this.company = requestDto.getCompany();
            this.phone = requestDto.getPhone();
            this.introduction = requestDto.getIntroduction();
        }
    }
    ```

- **양방향 순환 참조 해결**
  - 양방향 순환 참조 문제를 해결하기 위해 DTO를 새로 만듭니다.
  - **`LectureResponseDTO`**와 **`SimpleTeacherResponseDTO`**를 사용하여 참조가 없는 DTO를 활용합니다.
    ```java
    @Getter
    public class LectureResponseDTO {
        private int id;
        ...
        private SimpleTeacherResponseDTO teacher;

        public LectureResponseDTO(Lecture lecture) {
            this.id = lecture.getId();
            ...
            this.teacher = new SimpleTeacherResponseDTO(lecture.getTeacher());
        }
    }

    @Getter
    public class SimpleTeacherResponseDTO {
        private int id;
        ...
        private String name;

        public SimpleTeacherResponseDTO(Teacher teacher) {
            this.id = teacher.getId();
            ...
            this.name = teacher.getName();
        }
    }
    ```

- **즉시 로딩(Eager Loading)과 지연 로딩(Lazy Loading)**
  - **즉시 로딩**: 엔티티 조회 시 해당 엔티티와 연관된 모든 엔티티를 동시에 조회합니다. 불필요한 데이터 로딩과 쿼리 복잡도 증가가 있을 수 있습니다.
  - **지연 로딩**: 연관된 객체를 사용하게 될 때 조회합니다. 필요한 데이터만 로딩하지만 쿼리 수가 많아질 수 있습니다.

- **고아 객체**
  - 부모를 잃어버린 자식 엔티티를 방지하기 위해 CASCADE를 사용합니다.
  - **PERSIST**: 모든 엔티티를 영속화합니다.
  - **MERGE**: 병합 시 연관 엔티티도 병합합니다.
  - **REMOVE**: 삭제 시 연관 엔티티도 삭제합니다.
  - **REFRESH**: 값 변경 시 연관 값들도 반영합니다.
  - **DETACH**: 컨텍스트로부터 분리 시 연관 엔티티도 분리합니다.
  - **ALL**: 모든 Type을 적용합니다.
  - 예를 들어, 강사 데이터 삭제 시 강의와의 관계를 모두 제거하기 위해 `@PreRemove`를 사용합니다.
    ```java
    @PreRemove
    private void preRemove() {
        for (Lecture lecture : lectures) {
            lecture.removeTeacher(); // 관계 해제
        }
    }
    ```

- **Validation**
  - **`@Valid`**를 사용하여 RequestDTO의 잘못된 입력 값을 감지합니다.
  - **`BindingResult`**를 활용하여 유효성 검사 결과를 처리합니다.
    ```java
    @PutMapping("/teacher")
    public ResponseDTO updateTeacher(@RequestBody @Valid TeacherRequestDTO teacherRequestDTO, BindingResult bindingResult) {
        return lectureService.updateTeacher(id, teacherRequestDTO);
    }
    ```

- **스프링 AOP**
  - **AOP (Aspect-Oriented Programming)**: 관점 지향 프로그래밍을 통해 기능을 분리하고 모듈화하여 유지보수를 용이하게 합니다.
  - 횡단 관심사(cross-cutting concerns)를 통해 기능의 적용을 선택하는 모듈을 관리합니다.
