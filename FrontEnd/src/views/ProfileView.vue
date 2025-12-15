<template>
  <div class="site-detail-view">
    <Navbar />

    <LoadingSpinner v-if="loading" message="Cargando sitio..." />

    <div v-else-if="site" class="container">
      <div class="site-header">
        <div class="site-info">
          <h1>{{ site.nombre }}</h1>
          <div class="site-meta">
            <span class="site-type">{{ site.tipo }}</span>
            <RatingStars :rating="site.calificacionPromedio || 0" />
          </div>
        </div>
        <div class="site-actions">
          <button @click="addToList" class="btn-action">+ Agregar a lista</button>
          <button
            v-if="isAuthenticated"
            @click="editSite"
            class="btn-edit"
          >
            ✏️ Editar
          </button>
          <button
            v-if="isAuthenticated"
            @click="deleteSite"
            class="btn-delete"
          >
            🗑️ Eliminar
          </button>
        </div>
      </div>

<<<<<<< HEAD
      <div class="site-content">
        <div class="main-content">
          <section class="photos-section">
            <h2>Fotografías</h2>
            <div v-if="photos.length > 0" class="photos-grid">
              <PhotoCard
                v-for="photo in photos"
                :key="photo.id"
                :photo="photo"
                @delete="handleDeletePhoto"
              />
=======
        <div class="tab-content">
          
          <div v-if="activeTab === 'reviews'" class="reviews-list">
            <h2>Mis Reseñas</h2>
            <div v-if="reviews.length > 0" class="reviews-grid">
              <div v-for="review in reviews" :key="review.id" class="review-item-container">
                <router-link 
                  :to="`/sitios/${review.idSitio}`" 
                  class="review-card-link"
                >
                  <div class="review-header">
                    <h3>{{ review.nombreSitio }}</h3>
                    <RatingStars :rating="review.calificacion" :show-value="false" />
                  </div>
                  
                  <p class="review-text">{{ review.contenido }}</p>
                  
                  <div class="review-footer">
                    <small>{{ formatDate(review.fecha) }}</small>
                  </div>
                </router-link>
              </div>
            </div>
            <p v-else class="no-content">No has escrito reseñas aún.</p>
          </div>

          <div v-if="activeTab === 'photos'" class="photos-list">
            <h2>Mis Fotografías</h2>
            <div v-if="photos.length > 0" class="photos-grid">
              <div v-for="photo in photos" :key="photo.id" class="photo-item">
                <router-link :to="`/sitios/${photo.idSitio}`" class="photo-card-link">
                  <img :src="photo.url" :alt="photo.sitioNombre" />
                  <div class="photo-info">
                    <span>{{ photo.nombreSitio }}</span>
                  </div>
                </router-link>
              </div>
>>>>>>> 53acc713ec245b19d7175bb3b89c31e063f395f2
            </div>
            <p v-else class="no-content">No hay fotografías aún.</p>
            <button
              v-if="isAuthenticated"
              @click="showUploadPhoto = true"
              class="btn-secondary"
            >
              Subir Foto
            </button>
          </section>

<<<<<<< HEAD
          <section class="description-section">
            <h2>Descripción</h2>
            <p>{{ site.descripcion }}</p>
          </section>

          <section class="reviews-section">
            <div class="reviews-header">
              <h2>Reseñas ({{ reviews.length }})</h2>
              <button
                v-if="isAuthenticated"
                @click="showCreateReview = true"
                class="btn-primary"
              >
                Escribir Reseña
              </button>
            </div>

            <div v-if="reviews.length > 0" class="reviews-list">
              <ReviewCard
                v-for="review in reviews"
                :key="review.id"
                :review="review"
                @edit="handleEditReview"
                @delete="handleDeleteReview"
              />
            </div>
            <p v-else class="no-content">No hay reseñas aún. Sé el primero en dejar una.</p>
          </section>
=======
          <div v-if="activeTab === 'lists'" class="lists-section">
            <h2>Mis Listas</h2>
            <div v-if="listsStore.lists.length > 0">
               <router-link to="/mis-listas" class="btn-primary">
                 Ver todas mis listas
               </router-link>
            </div>
            <div v-else>
               <p class="no-content">No tienes listas creadas.</p>
               <router-link to="/mis-listas" class="btn-primary">
                 Crear una lista
               </router-link>
            </div>
          </div>
>>>>>>> 53acc713ec245b19d7175bb3b89c31e063f395f2
        </div>

        <aside class="sidebar">
          <div class="info-card">
            <h3>Ubicación</h3>
            <p v-if="site.latitud && site.longitud" class="coordinates">
              <strong>Latitud:</strong> {{ site.latitud }}<br />
              <strong>Longitud:</strong> {{ site.longitud }}
            </p>
            <p v-else class="no-coordinates">
              Coordenadas no disponibles
            </p>
            <div class="map-preview">
              <p>Vista previa del mapa</p>
              <p class="map-placeholder-text">El mapa se mostrará aquí en futuras versiones</p>
            </div>
          </div>
        </aside>
      </div>
    </div>

    <ReviewForm
      v-if="showCreateReview"
      :site-id="siteId"
      @close="showCreateReview = false"
      @created="onReviewCreated"
    />

    <ReviewEditForm
      v-if="showEditReview"
      :review="editingReview"
      @close="showEditReview = false"
      @updated="onReviewUpdated"
    />

    <PhotoUpload
      v-if="showUploadPhoto"
      :site-id="siteId"
      @close="showUploadPhoto = false"
      @uploaded="onPhotoUploaded"
    />

    <AddToListModal
      v-if="showAddToList"
      :site-id="siteId"
      @close="showAddToList = false"
      @added="showAddToList = false"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useSitesStore } from '@/stores/sites'
import { useReviewsStore } from '@/stores/reviews'
import { usePhotosStore } from '@/stores/photos'
import { useAuthStore } from '@/stores/auth'
import Navbar from '@/components/layout/Navbar.vue'
import RatingStars from '@/components/common/RatingStars.vue'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'
import ReviewForm from '@/components/reviews/ReviewForm.vue'
import ReviewEditForm from '@/components/reviews/ReviewEditForm.vue'
import ReviewCard from '@/components/reviews/ReviewCard.vue'
import PhotoUpload from '@/components/photos/PhotoUpload.vue'
import PhotoCard from '@/components/photos/PhotoCard.vue'
import AddToListModal from '@/components/lists/AddToListModal.vue'

const route = useRoute()
const router = useRouter()
const sitesStore = useSitesStore()
const reviewsStore = useReviewsStore()
const photosStore = usePhotosStore()
const authStore = useAuthStore()

const siteId = computed(() => route.params.id)
const site = computed(() => sitesStore.currentSite)
const reviews = computed(() => reviewsStore.reviews)
const photos = computed(() => photosStore.photos)
const isAuthenticated = computed(() => authStore.isAuthenticated)

const loading = ref(false)
const showCreateReview = ref(false)
const showEditReview = ref(false)
const showUploadPhoto = ref(false)
const showAddToList = ref(false)
const editingReview = ref(null)

const formatDate = (dateString) => {
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

<<<<<<< HEAD
const addToList = () => {
  if (!isAuthenticated.value) {
    alert('Debes iniciar sesión para agregar sitios a listas')
    return
  }
  showAddToList.value = true
}

const editSite = () => {
  router.push(`/sitios/${siteId.value}/editar`)
=======
const onProfileUpdated = () => {
  showEditProfile.value = false
  profileUser.value = { ...currentUser.value }
}

const onFollowChanged = () => {
  loadFollowersStats()
>>>>>>> 53acc713ec245b19d7175bb3b89c31e063f395f2
}

const deleteSite = async () => {
  if (!confirm('¿Estás seguro de eliminar este sitio? Esta acción no se puede deshacer y eliminará también todas sus reseñas y fotos.')) {
    return
  }

  try {
    await sitesStore.deleteSite(siteId.value)
    alert('Sitio eliminado exitosamente')
    router.push('/sitios')
  } catch (error) {
    alert('Error al eliminar el sitio')
  }
}

const handleEditReview = (review) => {
  editingReview.value = review
  showEditReview.value = true
}

const handleDeleteReview = async (reviewId) => {
  try {
    await reviewsStore.deleteReview(reviewId)
    await reviewsStore.fetchBySiteId(siteId.value)
  } catch (error) {
    alert('Error al eliminar la reseña')
  }
}

const handleDeletePhoto = async (photoId) => {
  try {
    await photosStore.deletePhoto(photoId)
    await photosStore.fetchBySiteId(siteId.value)
  } catch (error) {
    alert('Error al eliminar la fotografía')
  }
}

const onReviewCreated = async () => {
  showCreateReview.value = false
  await reviewsStore.fetchBySiteId(siteId.value)
}

const onReviewUpdated = async () => {
  showEditReview.value = false
  await reviewsStore.fetchBySiteId(siteId.value)
}

const onPhotoUploaded = async () => {
  showUploadPhoto.value = false
  await photosStore.fetchBySiteId(siteId.value)
}

onMounted(async () => {
  loading.value = true
  try {
<<<<<<< HEAD
    await Promise.all([
      sitesStore.fetchById(siteId.value),
      reviewsStore.fetchBySiteId(siteId.value),
      photosStore.fetchBySiteId(siteId.value)
    ])
  } finally {
    loading.value = false
  }
=======
    if (!currentUser.value) {
      router.push('/login')
      return
    }

    const userId = route.params.id ? parseInt(route.params.id) : currentUser.value?.id
    
    if (!userId) {
      console.error('No se pudo obtener el ID del usuario')
      router.push('/login')
      return
    }

    profileUserId.value = userId

    if (isOwnProfile.value) {
      profileUser.value = { ...currentUser.value }
    } else {
      const userData = await userService.getUserById(userId)
      profileUser.value = userData
    }

    try {
      await Promise.all([
        reviewsStore.fetchByUserId(userId).catch(err => {
          console.error('Error cargando reseñas:', err)
          return [] 
        }),
        photosStore.fetchByUserId(userId).catch(err => {
          console.error('Error cargando fotos:', err)
          return [] 
        }),
        listsStore.fetchByUserId(userId).catch(err => {
          console.error('Error cargando listas:', err)
          return [] 
        }),
        loadFollowersStats()
      ])
    } catch (err) {
      console.error('Error en carga paralela:', err)
    }

    stats.value = {
      totalResenas: reviews.value.length,
      totalFotos: photos.value.length,
      totalListas: listsStore.lists.length
    }
  } catch (error) {
    console.error('Error al cargar perfil:', error)
    if (error.response && (error.response.status === 401 || error.response.status === 403)) {
      router.push('/login')
    }
  } finally {
    loading.value = false
  }
}

watch(activeTab, async (newTab) => {
  if (!profileUserId.value) return

  if (newTab === 'reviews') {
    await reviewsStore.fetchByUserId(profileUserId.value)
    stats.value.totalResenas = reviews.value.length
  } else if (newTab === 'photos') {
    await photosStore.fetchByUserId(profileUserId.value)
    stats.value.totalFotos = photos.value.length
  } else if (newTab === 'lists') {
    await listsStore.fetchByUserId(profileUserId.value)
    stats.value.totalListas = listsStore.lists.length
  }
})

watch(() => route.params.id, () => {
  if (route.name === 'profile' || route.name === 'user-profile') {
    loadProfile()
  }
})

onMounted(() => {
  loadProfile()
>>>>>>> 53acc713ec245b19d7175bb3b89c31e063f395f2
})
</script>

<style scoped>
<<<<<<< HEAD
.site-detail-view {
=======
/* ESTILOS GENERALES */
.profile-view {
>>>>>>> 53acc713ec245b19d7175bb3b89c31e063f395f2
  min-height: 100vh;
  background-color: #f8f9fa;
  padding-bottom: 3rem;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

<<<<<<< HEAD
.site-header {
=======
/* HEADER DEL PERFIL */
.profile-header {
>>>>>>> 53acc713ec245b19d7175bb3b89c31e063f395f2
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  margin-bottom: 2rem;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}

.site-info h1 {
  margin: 0 0 1rem 0;
  color: #2c3e50;
}

.site-meta {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.site-type {
  background-color: #3498db;
  color: white;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.9rem;
}

.site-actions {
  display: flex;
  gap: 1rem;
}

.btn-action {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.btn-action:hover {
  background-color: #2980b9;
}

<<<<<<< HEAD
.btn-edit {
  background-color: #f39c12;
=======
/* ESTADÍSTICAS */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 1rem;
  margin-bottom: 2rem;
}

.stat-card {
  background-color: white;
  padding: 1.5rem;
  border-radius: 8px;
  text-align: center;
  transition: all 0.3s;
}

.stat-card.clickable {
  cursor: pointer;
}

.stat-card.clickable:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  background-color: #f8f9fa;
}

.stat-number {
  font-size: 2.5rem;
  font-weight: bold;
  color: #3498db;
  margin-bottom: 0.5rem;
}

.stat-label {
  color: #7f8c8d;
  font-size: 0.9rem;
}

/* PESTAÑAS */
.tabs {
  background-color: white;
  padding: 1rem;
  border-radius: 8px 8px 0 0;
  display: flex;
  gap: 0.5rem;
}

.tab-button {
  padding: 0.75rem 1.5rem;
  border: none;
  background: none;
  cursor: pointer;
  color: #7f8c8d;
  font-weight: 500;
  border-radius: 4px;
  transition: background-color 0.3s, color 0.3s;
}

.tab-button.active {
  background-color: #3498db;
>>>>>>> 53acc713ec245b19d7175bb3b89c31e063f395f2
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.btn-edit:hover {
  background-color: #e67e22;
}

.btn-delete {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.btn-delete:hover {
  background-color: #c0392b;
}

.site-content {
  display: grid;
  grid-template-columns: 1fr 350px;
  gap: 2rem;
}

.main-content {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

section {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
}

section h2 {
  margin: 0 0 1.5rem 0;
  color: #2c3e50;
}

<<<<<<< HEAD
=======
/* === ESTILOS PARA RESEÑAS (NUEVO: ESTILO TARJETA) === */
.reviews-grid {
  display: grid;
  /* Tarjetas con ancho mínimo de 300px, se ajustan solas */
  grid-template-columns: repeat(auto-fill, minmax(900px, 1fr));
  gap: 1.5rem;
}

.review-item-container {
  height: 100%; /* Para que todas las tarjetas tengan la misma altura */
}

.review-card-link {
  display: flex;
  flex-direction: column;
  height: 100%;
  
  background-color: white;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 1.5rem;
  
  text-decoration: none; /* Quita subrayado */
  color: inherit;      /* Mantiene texto negro */
  cursor: pointer;
  
  transition: all 0.3s ease;
}

/* Efecto Hover en la tarjeta de reseña */
.review-card-link:hover {
  transform: translateY(-4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-color: #3498db;
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 0.75rem;
}

.review-header h3 {
  margin: 0;
  font-size: 1.1rem;
  color: #2c3e50;
  font-weight: 600;
  transition: color 0.3s;
}

.review-card-link:hover h3 {
  color: #3498db; /* Título azul al pasar el mouse por la tarjeta */
}

.review-text {
  color: #34495e;
  margin: 0 0 1rem 0;
  line-height: 1.5;
  flex-grow: 1; /* Empuja el footer hacia abajo */

  overflow-wrap: break-word;
}

.review-footer small {
  color: #95a5a6;
  font-size: 0.85rem;
}


/* === ESTILOS PARA FOTOS (ESTILO TARJETA) === */
>>>>>>> 53acc713ec245b19d7175bb3b89c31e063f395f2
.photos-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 1rem;
}

<<<<<<< HEAD
.btn-primary,
.btn-secondary {
  background-color: #27ae60;
=======
.photo-item {
  aspect-ratio: 1;
  border-radius: 8px;
  overflow: hidden;
}

.photo-card-link {
  display: block;
  position: relative;
  width: 100%;
  height: 100%;
  text-decoration: none;
  color: inherit;
  cursor: pointer;
}

.photo-card-link img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s;
}

.photo-card-link:hover img {
  transform: scale(1.05); /* Zoom al pasar el mouse */
}

.photo-info {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
  padding: 1rem;
  color: white;
  pointer-events: none; /* Los clicks pasan a traves de esto hacia el link */
}

.photo-info span {
  font-weight: 500;
}

/* OTROS ESTILOS */
.no-content {
  text-align: center;
  color: #7f8c8d;
  padding: 2rem;
}

.btn-primary {
  display: inline-block;
  background-color: #3498db;
>>>>>>> 53acc713ec245b19d7175bb3b89c31e063f395f2
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.3s;
}

.btn-secondary {
  background-color: #95a5a6;
}

.btn-primary:hover {
  background-color: #229954;
}

.btn-secondary:hover {
  background-color: #7f8c8d;
}

.reviews-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  overflow-wrap: break-word;
}

.reviews-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;

  overflow-wrap: break-word;
}

.no-content {
  color: #7f8c8d;
  text-align: center;
  padding: 2rem 0;
}

.sidebar {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.info-card {
  background-color: white;
  padding: 1.5rem;
  border-radius: 8px;
}

.info-card h3 {
  margin: 0 0 1rem 0;
  color: #2c3e50;
}

.coordinates {
  color: #2c3e50;
  line-height: 1.8;
  margin: 0 0 1rem 0;
}

.no-coordinates {
  color: #95a5a6;
  font-style: italic;
  margin: 0 0 1rem 0;
}

.map-preview {
  background-color: #ecf0f1;
  border: 2px dashed #bdc3c7;
  border-radius: 6px;
  padding: 2rem;
  text-align: center;
  margin-top: 1rem;
}

.map-placeholder-text {
  color: #7f8c8d;
  font-size: 0.9rem;
  margin: 0.5rem 0 0 0;
}

@media (max-width: 968px) {
  .site-content {
    grid-template-columns: 1fr;
  }

  .site-header {
    flex-direction: column;
    gap: 1rem;
  }
}
</style>