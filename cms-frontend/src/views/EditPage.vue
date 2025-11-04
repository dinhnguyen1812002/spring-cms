// components/LandingPageEditor.vue
<script setup lang="ts">

import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import type { LandingPage } from "../types/LandingPage";
import { getLandingPage, updateLandingPage } from "../services/PageServices.ts";

const route = useRoute();
const loading = ref(false);
const activeTab = ref('html');
const showNotification = ref(false);
const notificationMessage = ref('');
const notificationType = ref<'success' | 'error'>('success');

const page = ref<LandingPage>({
  id: 0,
  name: "",
  slug: "",
  htmlContent: "",
  cssContent: "",
  jsContent: "",
  template: "",
  isPublished: false,
  url: "",
  createdAt: ""
});

onMounted(async () => {
  const slug = route.params.slug as string;
  if (!slug) {
    showNotification.value = true;
    notificationMessage.value = 'Slug is missing in URL';
    notificationType.value = 'error';
    return;
  }

  try {
    loading.value = true;
    page.value = await getLandingPage(slug); // ✅ truyền slug
  } catch (error) {
    showNotification.value = true;
    notificationMessage.value = 'Failed to load page content';
    notificationType.value = 'error';
    console.error("Error fetching page:", error);
  } finally {
    loading.value = false;
  }
});

const savePage = async () => {
  if (!page.value.slug) return;

  try {
    loading.value = true;
    // ✅ Cập nhật theo slug, không dùng ID
    await updateLandingPage(page.value.slug, page.value);
    showNotification.value = true;
    notificationMessage.value = 'Page saved successfully';
    notificationType.value = 'success';

    setTimeout(() => {
      showNotification.value = false;
    }, 3000);
  } catch (error) {
    showNotification.value = true;
    notificationMessage.value = 'Failed to save page';
    notificationType.value = 'error';
    console.error("Error saving page:", error);
  } finally {
    loading.value = false;
  }
};
</script>


<template>
  <div class="min-h-screen bg-gray-50">
    <!-- Notification -->
    <div
      v-if="showNotification"
      :class="{
        'fixed top-4 right-4 p-4 rounded-lg shadow-lg z-50 transition-all duration-300': true,
        'bg-green-100 text-green-800': notificationType === 'success',
        'bg-red-100 text-red-800': notificationType === 'error'
      }"
    >
      {{ notificationMessage }}
    </div>
    
    <!-- Header -->
    <div class="bg-white border-b px-6 py-4">
      <div class="flex items-center justify-between">
        <div class="flex items-center space-x-4">
          <h1 class="text-xl font-semibold text-gray-800">Edit Landing Page</h1>
          <span class="text-sm text-gray-500">ID: {{ page.id }}</span>
        </div>
        
        <button
          @click="savePage"
          :disabled="loading"
          class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed flex items-center space-x-2"
        >
          <svg v-if="loading" class="animate-spin h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
            <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
            <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
          </svg>
          <span>{{ loading ? 'Saving...' : 'Save Changes' }}</span>
        </button>
      </div>
    </div>

    <!-- Main Content -->
    <div class="container mx-auto px-4 py-6">
      <!-- Basic Info Section -->
      <div class="bg-white rounded-lg shadow-sm p-6 mb-6">
        <h2 class="text-lg font-medium text-gray-700 mb-4">Basic Information</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div class="flex flex-col">
            <label class="text-sm text-gray-600 mb-1">Page Name</label>
            <input
              v-model="page.name"
              type="text"
              class="px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
              placeholder="Enter page name"
            />
          </div>
          <div class="flex flex-col">
            <label class="text-sm text-gray-600 mb-1">Slug</label>
            <input
              v-model="page.slug"
              type="text"
              class="px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
              placeholder="page-url-slug"
            />
          </div>
        </div>
      </div>

      <!-- Content Editor -->
      <div class="bg-white rounded-lg shadow-sm p-6">
        <!-- Tabs -->
        <div class="border-b mb-4">
          <div class="flex space-x-4">
            <button
              v-for="tab in ['html', 'css', 'js', 'settings']"
              :key="tab"
              @click="activeTab = tab"
              :class="{
                'px-4 py-2 text-sm font-medium -mb-px': true,
                'text-blue-600 border-b-2 border-blue-600': activeTab === tab,
                'text-gray-500 hover:text-gray-700': activeTab !== tab
              }"
            >
              {{ tab.toUpperCase() }}
            </button>
          </div>
        </div>

        <!-- Tab Content -->
        <div class="mt-4">
          <!-- HTML Editor -->
          <div v-if="activeTab === 'html'" class="h-96">
            <textarea
              v-model="page.htmlContent"
              class="w-full h-full p-4 font-mono text-sm border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
              placeholder="Enter HTML content..."
            ></textarea>
          </div>

          <!-- CSS Editor -->
          <div v-if="activeTab === 'css'" class="h-96">
            <textarea
              v-model="page.cssContent"
              class="w-full h-full p-4 font-mono text-sm border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
              placeholder="Enter CSS styles..."
            ></textarea>
          </div>

          <!-- JavaScript Editor -->
          <div v-if="activeTab === 'js'" class="h-96">
            <textarea
              v-model="page.jsContent"
              class="w-full h-full p-4 font-mono text-sm border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
              placeholder="Enter JavaScript code..."
            ></textarea>
          </div>

          <!-- Settings -->
          <div v-if="activeTab === 'settings'" class="space-y-4">
            <div class="flex items-center space-x-2">
              <input
                type="checkbox"
                v-model="page.isPublished"
                class="rounded border-gray-300 text-blue-600 focus:ring-blue-500"
              />
              <label class="text-sm text-gray-700">Published</label>
            </div>
            
            <div class="flex flex-col">
              <label class="text-sm text-gray-600 mb-1">Template</label>
              <input
                v-model="page.template"
                type="text"
                class="px-3 py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none"
                placeholder="Select template"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>