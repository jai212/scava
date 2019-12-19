from enum import Enum, unique


@unique
class ProjectsAnalysisTasks(Enum):
    """Specification of available tasks in this workflow as Enumerates types.
    
    NOTE: Auto-generated by Workflow2TaskEnum on 2019-12-19T12:32:41.974Z    
    Do not edit this class manually
    """
    
    PROJECT_SOURCE = "ProjectSource"
    REPOSITORY_CLONER = "RepositoryCloner"
    PYTHON_REPOSITORY_ANALYZER = "PythonRepositoryAnalyzer"
    REPOSITORY_ANALYSIS_RESULT_SINK = "RepositoryAnalysisResultSink"
    JAVA_REPOSITORY_ANALYZER = "JavaRepositoryAnalyzer"
