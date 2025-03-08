<script setup lang="ts">
import { ref, computed } from 'vue';
import axios from 'axios';

interface CreateLandingPage {
  name: string;
  slug: string;
  template: string;
  htmlContent: string;
  cssContent: string;
  jsContent: string;
  isPublished: boolean;
}

const formData = ref<CreateLandingPage>({
  name: '',
  slug: '',
  template: '',
  htmlContent: '',
  cssContent: '',
  jsContent: '',
  isPublished: false
});

const loading = ref(false);
const error = ref('');
const success = ref(false);

const createSlug = (name: string): string => {
  return name
    .toLowerCase()
    .replace(/[^a-z0-9]+/g, '-')
    .replace(/(^-|-$)/g, '');
};

const handleNameChange = () => {
  formData.value.slug = createSlug(formData.value.name);
};

const isFormValid = computed(() => {
  return formData.value.name && 
         formData.value.slug && 
         formData.value.template;
});

const handleSubmit = async () => {
  if (!isFormValid.value) {
    error.value = 'Please fill in all required fields';
    return;
  }

  try {
    loading.value = true;
    error.value = '';
    success.value = false;

    const response = await axios.post('http://localhost:8080/api/landing-pages/create', formData.value, {
      headers: {
        'Content-Type': 'application/json',
      }
    });

    success.value = true;
    formData.value = {
      name: '',
      slug: '',
      template: '',
      htmlContent: '',
      cssContent: '',
      jsContent: '',
      isPublished: false
    };
  } catch (e) {
    if (axios.isAxiosError(e)) {
      error.value = e.response?.data?.message || e.message;
    } else {
      error.value = 'An unexpected error occurred';
    }
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div class="min-h-screen bg-gray-50 py-8">
    <div class="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Header -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900">Create Landing Page</h1>
        <p class="mt-2 text-sm text-gray-600">Fill in the details below to create a new landing page.</p>
      </div>

      <form @submit.prevent="handleSubmit" class="bg-white shadow-lg rounded-lg overflow-hidden">
        <!-- Basic Information Section -->
        <div class="p-6 border-b border-gray-200">
          <h2 class="text-xl font-semibold text-gray-800 mb-4">Basic Information</h2>
          <div class="grid grid-cols-1 gap-6 md:grid-cols-2">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Page Name *</label>
              <input
                v-model="formData.name"
                @input="handleNameChange"
                type="text"
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                placeholder="Enter page name"
              />
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Slug *</label>
              <input
                v-model="formData.slug"
                type="text"
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                placeholder="page-url-slug"
              />
            </div>

            <div class="md:col-span-2">
              <label class="block text-sm font-medium text-gray-700 mb-1">Template *</label>
              <input
                v-model="formData.template"
                type="text"
                required
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                placeholder="Select or enter template name"
              />
            </div>
          </div>
        </div>

        <!-- Content Section -->
        <div class="p-6 border-b border-gray-200">
          <h2 class="text-xl font-semibold text-gray-800 mb-4">Page Content</h2>
          <div class="space-y-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">HTML Content</label>
              <textarea
                v-model="formData.htmlContent"
                rows="6"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500"
                placeholder="Enter HTML content"
              ></textarea>
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">CSS Styles</label>
              <textarea
                v-model="formData.cssContent"
                rows="6"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 font-mono text-sm"
                placeholder="Enter CSS styles"
              ></textarea>
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">JavaScript Code</label>
              <textarea
                v-model="formData.jsContent"
                rows="6"
                class="w-full px-3 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 font-mono text-sm"
                placeholder="Enter JavaScript code"
              ></textarea>
            </div>
          </div>
        </div>

        <!-- Publication Settings -->
        <div class="p-6 border-b border-gray-200">
          <h2 class="text-xl font-semibold text-gray-800 mb-4">Publication Settings</h2>
          <label class="flex items-center">
            <input
              type="checkbox"
              v-model="formData.isPublished"
              class="rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
            />
            <span class="ml-2 text-sm text-gray-700">Publish page immediately after creation</span>
          </label>
        </div>

        <!-- Form Actions -->
        <div class="p-6 bg-gray-50 flex items-center justify-between">
          <p class="text-sm text-gray-500">* Required fields</p>
          <button
            type="submit"
            :disabled="loading || !isFormValid"
            class="inline-flex items-center px-6 py-3 border border-transparent rounded-md shadow-sm text-base font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span v-if="loading">
              <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              Creating...
            </span>
            <span v-else>Create Landing Page</span>
          </button>
        </div>

        <!-- Status Messages -->
        <div v-if="error || success" class="p-6 border-t border-gray-200">
          <div v-if="error" class="p-4 bg-red-50 border border-red-200 rounded-md">
            <div class="flex">
              <div class="flex-shrink-0">
                <svg class="h-5 w-5 text-red-400" viewBox="0 0 20 20" fill="currentColor">
                  <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd" />
                </svg>
              </div>
              <div class="ml-3">
                <p class="text-sm text-red-700">{{ error }}</p>
              </div>
            </div>
          </div>
          
          <div v-if="success" class="p-4 bg-green-50 border border-green-200 rounded-md">
            <div class="flex">
              <div class="flex-shrink-0">
                <svg class="h-5 w-5 text-green-400" viewBox="0 0 20 20" fill="currentColor">
                  <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
                </svg>
              </div>
              <div class="ml-3">
                <p class="text-sm text-green-700">Landing page created successfully!</p>
              </div>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>