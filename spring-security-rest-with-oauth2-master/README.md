
## Usage

```
curl -X POST -vu client:secret http://localhost:8080/oauth/token -H "Accept: application/json" -d "password=123456&username=stackbox&grant_type=password&scope=read&client_secret=secret&client_id=client"
```