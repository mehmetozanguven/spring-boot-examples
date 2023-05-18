// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  runtimeConfig: {
    public: {
      googleRecaptchaKey: process.env.NUXT_PUBLIC_GOOGLE_RECAPTCHA_KEY,
    },
  },
});
