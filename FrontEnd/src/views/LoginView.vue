<template>
  <div class="login-container">
    <div class="card">
      <h2>Iniciar sesión</h2>

      <form @submit.prevent="doLogin">
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

        <button type="submit">Ingresar</button>
      </form>

      <p class="register-text">
        ¿No tienes cuenta?
        <span @click="goRegister">Regístrate</span>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { login } from "@/services/authService"; // <-- usa tu función

const router = useRouter();

const email = ref("");
const password = ref("");

const doLogin = async () => {
  try {
    const response = await login(email.value, password.value);

    console.log("Login OK:", response.data);

    // puedes guardar el token si lo mandas desde backend
    if (response.data.token) {
      localStorage.setItem("token", response.data.token);
    }

    router.push("/"); // redirige al home

  } catch (err) {
    console.error("Error login:", err);
    alert("Credenciales incorrectas");
  }
};

const goRegister = () => {
  router.push("/register");
};
</script>

<style scoped>
/* mismo estilo que antes */
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


