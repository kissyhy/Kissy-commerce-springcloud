package cn.kissy.ecommerce.constant;

/**
 * 授权需要使用的一些常量信息
 * @ClassName AuthorityConstant
 * @Author kingdee
 * @Date 2022/4/7
 * @Version 1.0
 **/
public final class AuthorityConstant {

    /**
     * RSA私钥，除了授权中心以外，不暴露给任何的客户端
     */
    public static final String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCIbBEUpe2hLglHuM2wwZtoT8Lpy" +
            "/sF8BJTB+RfBd0dras5run5sRBRD8y3Sj793wyq5rTytgaL7BNqdWxggrFcxk0/lPdWVH3HxrypUPadNRYtnep8UiO/R5R7tRT+gW5yCYEq9yb3amF" +
            "/hn3WV+RQvWd3Xf3aL3dJsYrZik3mEhNpv+lg0kpddr2tLvM4nIYbIGeJmz91bbYtPTb9UK5mxCJ1ArIJyQGokFIMIbmeBxOWlqycwewFKRKt1bifPEf6QkZ626mwf7vv2FU+UxfkymtossZsWnVEz8Ng4cwwzSOp1pakNr+1cCxqSVliStWS" +
            "/+ZskeAvsR/qLwlpXOSRAgMBAAECggEAEor911ymbB4hzMnly3zFbuxIhhbfy/LRys9rzy6w23Pbrj+ENhhHK32GSfOhlEhZJHGvbr4nLaWtDe9gB8475Wxe26GX7PgXEKyGwEzs" +
            "+HlaxmCMmtFfYoSMPjRi4iUdqgp4sCpf9YlUL01MlXHk6sST57my45FIGZA0W1VdOURPWZNNPbVjpb0Z4e4oKRd8AeLF1/LYEOTUeLo01qZg0PlcscK13h" +
            "/vjehdfRAhZ/h6M2wCezuQemyGkqucg4qc2elutR2IllXTtBJWe21Gw+qJ13axtiejGzE0Epm6wiA+UGwlRLvGQ5+wzdznXHCn1eV1qGiAdZpTCPHf8mjPAQKBgQDvVCjtV7nTKM6qW3XboWqc0B0FSWRj6rVmUOS5LN+X9rzS37Y8vqiTu/9sfhNR2M+7xKbS9RJ9Ij/WrOjCuKt0AqAQ17GKOHLQ9CzGdWCP1MBhTEjm29rf" +
            "XxEKOR5SEMJEu6R8+rqGxHRQ2D73/uPJ3X7i/wZpAGt6qvIPVRrJmQKBgQCR7NH9RV7HH3vUWlflm5LI868hkbIzmnFYyERUaBZKTMpWgW3OIMWu7zZUyckEkCs16XBQFAbrqwPf8xGJ+/jZWuMj3ITjAeysKg1sl96U5zGIYvquHD4Gsygn+bsL47iNS3d7sQrK8uHASDEVmIKgYniFCoIhI7JfYG70qw39uQKBgQCd5LM/qqHB" +
            "Kun1wHNoG/0/SZnr0/i0ZrOYtgPAG1fEZRc8NDWBq/OBzAT+qcrLM7QRV/MLuH2jKsZdNiQ12ynaVqUNl4/c9dLiAkXMBMJZAe6OAosSK7ghqBGQ4hAb6kyQlg82GSCYKuHmOFSKvpG/qy68Sa4L9ifyfPdHoEkU+QKBgQCC+ufNhxu4bckPZXwp60eIujpDTmx+py0APNogTUFctaqUlR5De+UUzUCeQHT/dIxkmsEmD6PamykiNiJTEWl" +
            "fZ5Yj1UcuH8cZDbH4/CRc0VwDiJxr9YuzJdJwd1buKBu6L7fyvYnrerEpj2MHeFofreNxv2noZwsvUvalBHjNeQKBgQDbiXkWR7GrP/aJvb2TwpTEuxM9hVNTem/Cs5gtvaRKoJxQlVdzA8HdzjixU5sahQ8/AfwCszZU04gUPN84EfxhAq4Y8OVp9RKftjqqg/ukHFymuBu3gu4TH7R3YHTHQ+UUFC4cowYJEDEqd337Mfk36NnLqpP0ZM0EKSBBlVPX3g==";

    /**
     * 默认的 Token 超时时间，一天
     */
    public static final Integer DEFAULT_EXPIRE_DAY = 1;
}
