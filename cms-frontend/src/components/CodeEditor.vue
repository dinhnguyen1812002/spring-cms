<template>
  <div class="code-editor" :style="{ height: height }" ref="editorContainer">
    <MonacoEditor
      :value="modelValue"
      :language="language"
      :theme="theme"
      :options="editorOptions"
      @update:value="updateValue"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch, onBeforeUnmount } from 'vue';
import { MonacoEditor } from '@monaco-editor/vue';
import * as monaco from 'monaco-editor';

const props = defineProps({
  modelValue: {
    type: String,
    default: '',
  },
  language: {
    type: String,
    default: 'html',
    validator: (value: string) => ['html', 'css', 'javascript', 'json'].includes(value),
  },
  height: {
    type: String,
    default: '300px',
  },
  theme: {
    type: String,
    default: 'vs-dark',
  },
  readOnly: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(['update:modelValue']);
const editorContainer = ref<HTMLElement | null>(null);

const editorOptions = {
  automaticLayout: true,
  fontSize: 14,
  lineNumbers: 'on',
  minimap: { enabled: true },
  scrollBeyondLastLine: false,
  tabSize: 2,
  wordWrap: 'on',
  readOnly: props.readOnly,
};

const updateValue = (value: string) => {
  emit('update:modelValue', value);
};

// Handle window resize for better editor layout
let resizeObserver: ResizeObserver | null = null;

onMounted(() => {
  if (editorContainer.value) {
    resizeObserver = new ResizeObserver(() => {
      // Trigger editor layout when container size changes
      window.dispatchEvent(new Event('resize'));
    });
    resizeObserver.observe(editorContainer.value);
  }
});

onBeforeUnmount(() => {
  if (resizeObserver) {
    resizeObserver.disconnect();
  }
});
</script>

<style scoped>
.code-editor {
  border: 1px solid #2d2d2d;
  border-radius: 4px;
  overflow: hidden;
  width: 100%;
}

:deep(.monaco-editor .margin) {
  background-color: #1e1e1e;
}

:deep(.monaco-editor) {
  --vscode-editor-background: #1e1e1e;
  --vscode-editor-foreground: #d4d4d4;
  --vscode-editorLineNumber-foreground: #858585;
  --vscode-editorLineNumber-activeForeground: #c6c6c6;
  --vscode-editor-selectionBackground: #264f78;
  --vscode-editor-cursorForeground: #aeafad;
  --vscode-editor-inactiveSelectionBackground: #3a3d41;
  --vscode-editor-lineHighlightBackground: #2a2d2e;
  --vscode-editor-lineHighlightBorder: #282828;
}
</style>
