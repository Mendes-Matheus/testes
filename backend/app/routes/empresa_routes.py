from flask import Blueprint, jsonify, request
from app.services.empresa_service import get_all_companies, search_companies

empresa_bp = Blueprint('empresas', __name__)

@empresa_bp.route('/empresas', methods=['GET'])
def listar_empresas():
    """Retorna todas as empresas ordenadas por Razão Social"""
    return jsonify(get_all_companies())

@empresa_bp.route('/search', methods=['GET'])
def buscar_empresa():
    """Busca empresas pelo nome"""
    query = request.args.get('query', '').strip()
    return jsonify(search_companies(query))
