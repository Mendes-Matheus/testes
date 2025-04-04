<template>
    <div class="container">
      <h1>Buscar Empresa</h1>
      <input 
        v-model="query" 
        @input="search" 
        placeholder="Digite a Razão Social..." 
        class="search-input"
      />
      
      <table v-if="results.length > 0">
        <thead>
          <tr>
            <th>Razão Social</th>
            <th>CNPJ</th>
            <th>Endereço</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(empresa, index) in results" :key="index">
            <td>{{ empresa.Razao_Social }}</td>
            <td>{{ empresa.CNPJ }}</td>
            <td>{{ empresa.Endereco }}</td>
          </tr>
        </tbody>
      </table>
      
      <p v-if="query && results.length === 0">Nenhum resultado encontrado.</p>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        query: '',
        results: []
      };
    },
    methods: {
      async search() {
        if (this.query.length < 3) {
          this.results = [];
          return;
        }
        
        try {
          const response = await axios.get('http://127.0.0.1:5000/search', {
            params: { query: this.query }
          });
          this.results = response.data;
        } catch (error) {
          console.error('Erro ao buscar dados:', error);
        }
      }
    }
  };
  </script>
  
  <style>
  .container {
    max-width: 600px;
    margin: auto;
    text-align: center;
  }
  .search-input {
    width: 100%;
    padding: 10px;
    margin-bottom: 20px;
    font-size: 16px;
  }
  table {
    width: 100%;
    border-collapse: collapse;
  }
  th, td {
    padding: 10px;
    border: 1px solid #ddd;
  }
  th {
    background-color: #f4f4f4;
  }
  </style>
  