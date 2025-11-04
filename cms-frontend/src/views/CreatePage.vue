<script setup lang="ts">
import { ref, computed } from 'vue';
import axios from 'axios';
import {createLandingPage} from "../services/PageServices.ts";

interface CreateLandingPage {
  name: string;
  slug: string;
  template: string;
  htmlContent: string;
  cssContent: string;
  jsContent: string;
  isPublished: boolean;
}

const sections = {
  basic: { title: 'Thông tin cơ bản', icon: 'information-circle' },
  content: { title: 'Nội dung trang', icon: 'document-text' },
  settings: { title: 'Cài đặt xuất bản', icon: 'cog' }
};

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
const activeTab = ref('basic');

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
    error.value = 'Vui lòng điền đầy đủ thông tin bắt buộc';
    return;
  }

  try {
    loading.value = true;
    error.value = '';
    success.value = false;

    await createLandingPage(formData.value);

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
      error.value = 'Đã xảy ra lỗi không mong muốn';
    }
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div class="min-h-screen bg-gray-100">
    <div class="bg-gradient-to-r from-indigo-600 to-purple-600 text-white py-8">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h1 class="text-4xl font-bold">Tạo Landing Page</h1>
        <p class="mt-2 text-indigo-100">Thiết kế và tạo landing page mới của bạn</p>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div class="flex space-x-4 mb-8 border-b">
        <button
          v-for="(section, key) in sections"
          :key="key"
          @click="activeTab = key"
          :class="[
            'px-4 py-2 font-medium text-sm rounded-t-lg transition',
            activeTab === key
              ? 'text-indigo-600 border-b-2 border-indigo-600 bg-white'
              : 'text-gray-500 hover:text-gray-700'
          ]"
        >
          <i :class="'fas fa-' + section.icon" class="mr-2"></i>
          {{ section.title }}
        </button>
      </div>

      <form @submit.prevent="handleSubmit" class="bg-white rounded-xl shadow-xl">
        <div v-show="activeTab === 'basic'" class="p-6 space-y-6">
          <div class="grid grid-cols-1 gap-6 md:grid-cols-2">
            <div class="space-y-2">
              <label class="text-sm font-medium text-gray-700">Tên trang *</label>
              <input
                v-model="formData.name"
                @input="handleNameChange"
                type="text"
                class="w-full px-4 py-2 rounded-lg border focus:ring-2 focus:ring-indigo-500"
                placeholder="Nhập tên trang"
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

        <div v-show="activeTab === 'content'" class="p-6 space-y-6">
          <div class="grid grid-cols-1 gap-8">
            <div class="space-y-2">
              <label class="text-sm font-medium text-gray-700">HTML</label>
              <div class="relative">
                <textarea
                  v-model="formData.htmlContent"
                  class="w-full h-64 px-4 py-3 font-mono text-sm bg-gray-50 rounded-lg border"
                  placeholder="<div>Your HTML here</div>"
                ></textarea>
                <div class="absolute top-2 right-2 bg-gray-200 px-2 py-1 rounded text-xs">HTML</div>
              </div>
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

        <div v-show="activeTab === 'settings'" class="p-6">
          <div class="bg-gray-50 p-4 rounded-lg">
            <label class="flex items-center space-x-3">
              <input
                type="checkbox"
                v-model="formData.isPublished"
                class="w-5 h-5 text-indigo-600 rounded"
              />
              <span class="text-gray-700">Xuất bản ngay sau khi tạo</span>
            </label>
          </div>
        </div>

        <div class="p-6 bg-gray-50 rounded-b-xl flex items-center justify-between">
          <p class="text-sm text-gray-500">* Trường bắt buộc</p>
          <button
            type="submit"
            :disabled="loading || !isFormValid"
            class="px-6 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 disabled:opacity-50 transition"
          >
            <span v-if="loading" class="flex items-center">
              <svg class="animate-spin h-5 w-5 mr-2" viewBox="0 0 24 24">
                <!-- Loading spinner path -->
              </svg>
              Đang tạo...
            </span>
            <span v-else>Tạo Landing Page</span>
          </button>
        </div>

        <div v-if="error || success" class="p-4 mx-6 mb-6 rounded-lg" :class="{
          'bg-red-50 border border-red-200': error,
          'bg-green-50 border border-green-200': success
        }">
          <p :class="[
            'text-sm',
            error ? 'text-red-700' : 'text-green-700'
          ]">
            {{ error || 'Tạo landing page thành công!' }}
          </p>
        </div>
      </form>
    </div>
  </div>
</template>


