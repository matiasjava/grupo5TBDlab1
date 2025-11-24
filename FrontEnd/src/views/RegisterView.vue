<template>
  <div class="login-container">
    <div class="card">
      <h2>Crear cuenta</h2>

      <form @submit.prevent="doRegister">

        <input 
          type="text" 
          v-model="nombre"
          placeholder="Nombre completo"
          required
        />

        <input 
          type="email" 
          v-model="email"
          placeholder="Correo"
          required
        />

        <input 
          type="password" 
          v-model="password"
          placeholder="Contraseña"
          required
        />

        <textarea
          v-model="biografia"
          placeholder="Biografía (opcional)"
          rows="3"
          style="width:100%; padding:12px; border-radius:10px; border:1px solid #ccc; margin-bottom:14px;"
        ></textarea>

        <button type="submit">Registrarme</button>
      </form>

      <p class="register-text">
        ¿Ya tienes cuenta?
        <span @click="goLogin">Iniciar sesión</span>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { register } from "@/services/authService"; // <-- tu función de backend

const router = useRouter();

const nombre = ref("");
const email = ref("");
const password = ref("");
const biografia = ref("");

const doRegister = async () => {
  try {
    await register(
      nombre.value,
      email.value,
      password.value,
      biografia.value
    );

    alert("Cuenta creada con éxito");
    router.push("/login");

  } catch (err) {
    console.error("Error register:", err);
    alert("Error al crear cuenta");
  }
};

const goLogin = () => {
  router.push("/login");
};
</script>

<style scoped>
/* mismo estilo que login */
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f1f3f5;
}

.card {
  width: 360px;
  padding: 32px;
  border-radius: 16px;
  background: white;
  box-shadow: 0 4px 30px rgba(0,0,0,0.1);
  text-align: center;
}

.card h2 {
  margin-bottom: 20px;
}

input {
  width: 100%;
  padding: 12px;
  margin-bottom: 14px;
  border-radius: 10px;
  border: 1px solid #ccc;
}

button {
  width: 100%;
  padding: 12px;
  border: none;
  border-radius: 10px;
  background: #1d9bf0;
  color: white;
  font-size: 16px;
  cursor: pointer;
  margin-top: 8px;
}
button:hover {
  background: #0c7ac7;
}

.register-text {
  margin-top: 18px;
  font-size: 14px;
}

.register-text span {
  color: #1d9bf0;
  font-weight: 600;
  cursor: pointer;
}
</style>

