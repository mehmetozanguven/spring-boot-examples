import { VueReCaptcha } from "vue-recaptcha-v3";
import { IReCaptchaOptions } from "vue-recaptcha-v3/dist/IReCaptchaOptions";
// The plugin enables the usage of Google reCAPTCHA in a Nuxt.js application
// by registering the VueReCaptcha plugin with the necessary configuration options.
export default defineNuxtPlugin((nuxtApp) => {
  // The useRuntimeConfig function is called to retrieve the runtime
  // configuration of the Nuxt.js application.
  const config = useRuntimeConfig();

  const options: IReCaptchaOptions = {
    siteKey: config.public.googleRecaptchaKey,
    loaderOptions: {
      useRecaptchaNet: true,
    },
  };
  nuxtApp.vueApp.use(VueReCaptcha, options);
});
