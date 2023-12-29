package com.seven.encode;

import lombok.SneakyThrows;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

/**
 * Java加密的常用的加密算法类型有三种：
 * <p>
 * 【1】单向加密：也就是不可逆的加密，例如MD5,SHA,HMAC
 * 【2】对称加密：也就是加密方和解密方利用同一个秘钥对数据进行加密和解密，例如DES，PBE等等
 * 【3】非对称加密：非对称加密分为公钥和秘钥，二者是非对称的，例如用私钥加密的内容需要使用公钥来>解密，使用公钥加密的内容需要用私钥来解密，DSA，RSA…
 * 而keyGenerator,KeyPairGenerator,SecretKeyFactory的三种使用方法刚好和这三种加密算法类型对上：
 * <p>
 * 【1】keyGenerator：秘钥生成器，也就是更具算法类型随机生成一个秘钥，例如HMAC，所以这个大部分用在非可逆的算法中
 * 【2】SecretKeyFactory：秘密秘钥工厂，言外之意就是需要根据一个秘密（password）去生成一个秘钥,例如DES，PBE，所以大部分使用在对称加密中
 * 【3】KeyPairGenerator:秘钥对生成器，也就是可以生成一对秘钥，也就是公钥和私钥，所以大部分使用在非对称加密中
 * ————————————————
 * 版权声明：本文为CSDN博主「书香水墨」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/qq_27870421/article/details/103779481
 */
public class EncodeTest {
    @Test
    public void testBase64() {
        String str = "123456";
        // 加密
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(str.getBytes());
        System.out.println(new String(encode)); // MTIzNDU2

        // 解密
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(encode);
        System.out.println(new String(decode));
        // url编码
        Base64.Encoder urlEncoder = Base64.getUrlEncoder();
        byte[] encode1 = urlEncoder.encode(str.getBytes());
        System.out.println(new String(encode1)); // MTIzNDU2
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
    }

    /**
     * MD2	128	JDK
     * MD5	128	JDK
     * SHA-1	160	JDK
     * SHA-224	224	Bouncy Castle
     * SHA2561	256	JDK
     * SHA-384	384	JDK
     * SHA-512	512	JDK
     */
    @Test
    public void testMessageDigest() {
        String str = "123456";
        // 加密
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            MessageDigest md2 = MessageDigest.getInstance("MD2");
            MessageDigest sha = MessageDigest.getInstance("SHA");
            byte[] digest = md5.digest(str.getBytes());
            System.out.println("MD5: " + new BigInteger(1, digest).toString(16));
            byte[] digest1 = md2.digest(str.getBytes());
            System.out.println("MD2: " + Arrays.toString(digest1));
            System.out.println("SHA: " + Arrays.toString(sha.digest(str.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * HmacMD2	128	Bouncy Castle
     * HmacMD4	128	Bouncy Castle
     * HmacMD5	128	JDK
     * HmacSHA1	160	JDK
     * HmacSHA224	224	Bouncy Castle
     * HmacSHA256	256	JDK
     * HmacSHA384	384	JDK
     * HmacSHA512	512	JDK
     */
    @SneakyThrows
    @Test
    public void testHmac() {
        String data = "123456";
        // 自定义密钥
        KeyGenerator hmacMD5 = KeyGenerator.getInstance("HmacSHA224");
        hmacMD5.init(56); // 初始化密钥长度
        SecretKey secretKey = hmacMD5.generateKey();
        System.out.println("自定义密钥: " + new BigInteger(secretKey.getEncoded()).toString(16));
        // 手动生成密钥
        // secretKey = new SecretKeySpec("123456".getBytes(), "HmacSHA224");
        System.out.println("手动生成密钥: " + new BigInteger(secretKey.getEncoded()).toString(16));
        Mac mac = Mac.getInstance(secretKey.getAlgorithm()); // 实例化Mac对象
        mac.init(secretKey); // 初始化Mac对象
        byte[] bytes = mac.doFinal(data.getBytes()); // 执行Mac对象
        System.out.println("HmacMD5: " + new BigInteger(1, bytes).toString(16));
    }

    /**
     * 算法类型 密钥长度 默认长度 工作模式 填充方式
     * DES  56	56	ECB、CBC、PCBC、CTR、CTS、CFB、CFB8到128、OFB、OFB8到128	NoPadding、PKCS5Padding、ISO10126Padding	JDK
     * DES  64	56	ECB、CBC、PCBC、CTR、CTS、CFB、CFB8到128、OFB、OFB8到128	PKCS7Padding、ISO1012d2Padding、X932Padding、ZeroBytePadding	Bouncy Castle
     * 3DES 112、168	168	ECB、CBC、PCBC、CTR、CTS、CFB、CFB8到128、OFB、OFB8到128	NoPadding、PKCS5Padding、ISO10126Padding	JDK
     * 3DES 128、192	168	ECB、CBC、PCBC、CTR、CTS、CFB、CFB8到128、OFB、OFB8到128	PKCS7Padding、ISO1012d2Padding、X932Padding、ZeroBytePadding	Bouncy Castle
     */
    @SneakyThrows
    @Test
    public void testDes() {
// 原始字符
        String data = "Hello World!";

        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);      //指定key长度，同时也是密钥长度(56位)
        // keyGenerator.init(new SecureRandom());   //或者使用这种方式默认长度，无需指定长度
        SecretKey secretKey = keyGenerator.generateKey(); //生成key的材料
        byte[] key = secretKey.getEncoded();  //生成key

        // key转换成密钥（自己指定字节数组时需要转换）
        DESKeySpec desKeySpec = new DESKeySpec(key);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
        SecretKey key2 = factory.generateSecret(desKeySpec);      //转换后的密钥

        // 加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  //算法类型/工作方式/填充方式
        cipher.init(Cipher.ENCRYPT_MODE, key2);   //指定为加密模式
        byte[] result = cipher.doFinal(data.getBytes());
        BigInteger bigInteger = new BigInteger(1, result);
        System.out.println("jdkDES加密: " + bigInteger.toString(16));  //转换为十六进制
        // 解密
        cipher.init(Cipher.DECRYPT_MODE, key2);  //相同密钥，指定为解密模式
        result = cipher.doFinal(result);   //根据加密内容解密
        System.out.println("jdkDES解密: " + new String(result));  //转换字符串

    }

    /**
     * 算法类型 密钥长度 默认长度 工作模式 填充方式
     * 3DES 112、168	168	ECB、CBC、PCBC、CTR、CTS、CFB、CFB8到128、OFB、OFB8到128	NoPadding、PKCS5Padding、ISO10126Padding	JDK
     * 3DES 128、192	168	ECB、CBC、PCBC、CTR、CTS、CFB、CFB8到128、OFB、OFB8到128	PKCS7Padding、ISO1012d2Padding、X932Padding、ZeroBytePadding	Bouncy Castle
     */
    @SneakyThrows
    @Test
    public void test3Des() {
        // 原始字符
        String data = "Hello World!";

        // 生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        keyGenerator.init(112);      //3DES需要112 or 168位
        keyGenerator.init(new SecureRandom());   //或者使用这种方式默认长度，无需指定长度
        SecretKey secretKey = keyGenerator.generateKey(); //生成key的材料
        byte[] key = secretKey.getEncoded();  //生成key

        // key转换成密钥（自己指定字节数组时需要转换）
        DESedeKeySpec desKeySpec = new DESedeKeySpec(key);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        SecretKey key2 = factory.generateSecret(desKeySpec);      //转换后的密钥

        //加密
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");  //算法类型/工作方式/填充方式
        cipher.init(Cipher.ENCRYPT_MODE, key2);   //指定为加密模式
        byte[] result = cipher.doFinal(data.getBytes());
        BigInteger bigInteger = new BigInteger(1, result);
        System.out.println("jdk3DES加密: " + bigInteger.toString(16));  //转换为十六进制

        //解密
        cipher.init(Cipher.DECRYPT_MODE, key2);  //相同密钥，指定为解密模式
        result = cipher.doFinal(result);   //根据加密内容解密
        System.out.println("jdk3DES解密: " + new String(result));  //转换字符串
    }

    /**
     * 算法类型 密钥长度 默认长度 工作模式 填充方式
     * AES  112、192、256	128	ECB、CBC、PCBC、CTR、CTS、CFB、CFB8到128、OFB、OFB8到128	NoPadding、PKCS5Padding、ISO10126Padding	JDK
     * AES  112、192、256	128	ECB、CBC、PCBC、CTR、CTS、CFB、CFB8到128、OFB、OFB8到128	PKCS7Padding、ZeroBytePadding	Bouncy Castle
     */
    @SneakyThrows
    @Test
    public void testAes() {
// 原始字符
        String data = "Hello World!";

        //生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] key1 = secretKey.getEncoded();
        //key转换为密钥
        Key key2 = new SecretKeySpec(key1, "AES");

        //加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5padding");
        cipher.init(Cipher.ENCRYPT_MODE, key2);
        byte[] result = cipher.doFinal(data.getBytes());
        BigInteger bigInteger = new BigInteger(1, result);
        System.out.println("jdkAES加密: " + bigInteger.toString(16));  //转换为十六进制

        //解密
        cipher.init(Cipher.DECRYPT_MODE, key2);
        result = cipher.doFinal(result);
        System.out.println("jdkAES解密: " + new String(result));  //转换字符串
    }

    /**
     * PBE
     * 基于口令的对称加密算法（它其实是对之前的算法的包装，比如说MD5和DES，我这里就是的是对MD5和DES包装的PBE算法，还有其他类型的PBE），
     * 口令就是我们俗话说的密码，PBE中有一个salt（盐）的概念，盐就是干扰码
     */
    @SneakyThrows
    @Test
    public void testPbe() {
        // 原始字符
        String data = "Hello World!";

        //初始化盐
        SecureRandom random = new SecureRandom();
        byte[] salt = random.generateSeed(8);   // 指定为8位的盐 （盐就是干扰码，通过添加干扰码增加安全）

        // 口令和密钥
        String password = "TEST";              // 口令
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
        Key key = factory.generateSecret(pbeKeySpec);  // 密钥
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);   // 参数规范，第一个参数是盐，第二个是迭代次数（经过散列函数多次迭代）
        Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");

        //加密
        cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
        byte[] result = cipher.doFinal(data.getBytes());
        System.out.println("jdk PBE加密: " + Base64.getEncoder().encodeToString(result));

        //解密
        cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
        result = cipher.doFinal(result);
        System.out.println("jdk PBE解密: " + new String(result));
    }

    /**
     * 基本的非对称算法有DH，RSA，ELGamal算法
     * DH
     * 基于交换交换的非对称算法，发送方需要得到接收方的key构建本地密钥，而接收方也需要得到发送方的key构建自己本地的密钥
     */
    @SneakyThrows
    @Test
    public void testDh() {
        // 原始字符
        String data = "Hello World!";

        //初始化发送方密钥
        KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
        senderKeyPairGenerator.initialize(512);   //密钥长度
        KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
        byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();  //发送方key,需传递给接收方（网络，文件）

        //初始化接收方密钥
        KeyFactory factory = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);  //根据从发送方得到的key解析
        PublicKey receiverPublicKey = factory.generatePublic(x509EncodedKeySpec);
        DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams();

        KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
        receiverKeyPairGenerator.initialize(dhParameterSpec);
        KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
        PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
        byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();

        //密钥构建
        KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
        receiverKeyAgreement.init(receiverPrivateKey);
        receiverKeyAgreement.doPhase(receiverPublicKey, true);
        SecretKey receiverDESKey = receiverKeyAgreement.generateSecret("DES");  //发送发密钥(公钥)
        KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
        x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
        PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);

        KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
        senderKeyAgreement.init(senderKeyPair.getPrivate());
        senderKeyAgreement.doPhase(senderPublicKey, true);
        SecretKey senderDESKey = senderKeyAgreement.generateSecret("DES");        //接收方密钥(私钥)
        if (Objects.equals(receiverDESKey, senderDESKey)) {
            System.out.println("双方密钥相同");
        }
        //加密
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, senderDESKey);
        byte[] result = cipher.doFinal(data.getBytes());
        String encode = Base64.getEncoder().encodeToString(result);
        System.out.println("jdk DH加密: " + encode);

        //解密
        cipher.init(Cipher.DECRYPT_MODE, receiverDESKey);
        result = cipher.doFinal(result);
        System.out.println("jdk DH解密: " + new String(result));
    }

    /**
     * RSA相较于DH算法的实现简单，适用范围较广，公钥和私钥的创建较简单，而且支持公钥加密，私钥解密或者是私钥加密，公钥解密两种方式。
     */
    @SneakyThrows
    @Test
    public void testRsa() {
        // 原始字符
        String data = "Hello World!";

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();           //公钥
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();       //私钥
        System.out.println("public key:" + Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded()));
        System.out.println("private key:" + Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded()));

        //私钥加密，公钥解密--加密
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(data.getBytes());
        System.out.println("RSA私钥加密，公钥解密--加密:" + Base64.getEncoder().encodeToString(result));

        //私钥加密，公钥解密--解密
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        result = cipher.doFinal(result);
        System.out.println("RSA私钥加密，公钥解密--解密:" + new String(result));

        //公钥加密，私钥解密--加密
        x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        result = cipher.doFinal(data.getBytes());
        System.out.println("RSA公钥加密，私钥解密--加密:" + Base64.getEncoder().encodeToString(result));

        //公钥加密，私钥解密--解密
        pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        result = cipher.doFinal(result);
        System.out.println("RSA公钥加密，私钥解密--解密:" + new String(result));
    }
}
