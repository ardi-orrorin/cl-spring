## Client 개발 세팅

### 1. install package

```bash
npm i
```

### 2. env 설정

[env 파일](./.env)

VITE_SERVER_URL = 서버 주소
VITE_API_PREFIX_URL = API Prefix

### 3. run dev

```bash
npm run dev
```

### 4. Routing 규칙

- /src/router/[url]
  - index.vue : index 웹페이지
  - layout.vue : layout 웹페이지
  - (feature) : 웹 기능들
    - services : API 호출, Type 정의, 함수 정의 등
    - components : index 페이지에서 쓰이는 컴포넌트
    - hooks : index 페이지에서 쓰이는 hook

### 5. services ts 파일 정의

1. type 정의의 경우 namespace를 통해 클래스(묶음단위)를 정의하여 사용
2. namespace내에 정의 할때는 suffix 제거
3. type 의 경우 suffix로 T를 추가하여 구분 TestT
4. interface의 경우 suffix로 I를 추가하여 구분 TestI
5. 계층 구조로 할 경우 namespace내에 namespace를 추가하여 구분 namespaceName.namespaceName
6. enum의 경우 namespace내에 정의

```
type TestT = {}
interface TestI {}

export namespace namespaceName {
    export type type = typeT;
    export interface interface = interfaceI;

    export namespace namespaceName {
        export type type = typeT;
        export interface interface = interfaceI;
    }

    export enum enumName {

    }
}
```

### 6. services api 정의

1. url 이름 + serviceApi 객체로 export
2. axiosInstance를 통해 api 호출
3. method명은 jpa 메서드 규칙을 최대한 활용 (findAll, findById, save, delete)

```
const findbyAll = () => axiosInstance.get()

export const testServiceApi = {
    findbyAll
}
```
