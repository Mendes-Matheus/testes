from flask import Flask
from flask_cors import CORS
from app.routes.empresa_routes import empresa_bp

def create_app():
    app = Flask(__name__)
    CORS(app)

    app.register_blueprint(empresa_bp)

    return app
