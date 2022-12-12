import Alpine from "alpinejs";

Alpine.data("anotherAlpineComponent", () => ({
  init() {
    console.log("anotherAlpineComponent works !! ");
  },
}));

window.Alpine = Alpine;

Alpine.start();