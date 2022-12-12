import Alpine from "alpinejs";

Alpine.data("dummyAlpineComponent", () => ({
  init() {
    console.log("Alpine works !! ");
  },
}));

window.Alpine = Alpine;

Alpine.start();