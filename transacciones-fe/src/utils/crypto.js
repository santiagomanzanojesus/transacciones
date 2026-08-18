import CryptoJS from "crypto-js";

const SECRET_KEY = "miClaveSecretaParaEncriptar12345";

export const encryptAES = (text) => {
  const key = CryptoJS.enc.Utf8.parse(SECRET_KEY);
  const encrypted = CryptoJS.AES.encrypt(text, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7,
  });
  return encrypted.toString();
};
