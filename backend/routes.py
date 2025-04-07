from fastapi import FastAPI
from fastapi.responses import JSONResponse
from pydantic import BaseModel
from typing import List, Set
from expert_system import TomatoExpertSystem

app = FastAPI()
expert_system = TomatoExpertSystem()

class DiagnosisRequest(BaseModel):
    condition_codes: List[str]

class DiagnosisResponse(BaseModel):
    rule_id: str
    disease: str
    solutions: List[str]
    matched_conditions: List[str]

@app.post("/diagnose", response_model=List[DiagnosisResponse])
def diagnose(request: DiagnosisRequest):
    input_conditions = set(request.condition_codes)
    result = expert_system.forward_chain(input_conditions)

    if isinstance(result, list) and "message" in result[0]:  
        return JSONResponse(content=result[0], status_code=404)  

    return result
@app.get("/mappings")
def get_mappings():
    return expert_system.get_mappings()