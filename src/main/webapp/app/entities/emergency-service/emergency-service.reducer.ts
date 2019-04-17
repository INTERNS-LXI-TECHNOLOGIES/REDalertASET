import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmergencyService, defaultValue } from 'app/shared/model/emergency-service.model';

export const ACTION_TYPES = {
  FETCH_EMERGENCYSERVICE_LIST: 'emergencyService/FETCH_EMERGENCYSERVICE_LIST',
  FETCH_EMERGENCYSERVICE: 'emergencyService/FETCH_EMERGENCYSERVICE',
  CREATE_EMERGENCYSERVICE: 'emergencyService/CREATE_EMERGENCYSERVICE',
  UPDATE_EMERGENCYSERVICE: 'emergencyService/UPDATE_EMERGENCYSERVICE',
  DELETE_EMERGENCYSERVICE: 'emergencyService/DELETE_EMERGENCYSERVICE',
  RESET: 'emergencyService/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmergencyService>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type EmergencyServiceState = Readonly<typeof initialState>;

// Reducer

export default (state: EmergencyServiceState = initialState, action): EmergencyServiceState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMERGENCYSERVICE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMERGENCYSERVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMERGENCYSERVICE):
    case REQUEST(ACTION_TYPES.UPDATE_EMERGENCYSERVICE):
    case REQUEST(ACTION_TYPES.DELETE_EMERGENCYSERVICE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMERGENCYSERVICE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMERGENCYSERVICE):
    case FAILURE(ACTION_TYPES.CREATE_EMERGENCYSERVICE):
    case FAILURE(ACTION_TYPES.UPDATE_EMERGENCYSERVICE):
    case FAILURE(ACTION_TYPES.DELETE_EMERGENCYSERVICE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMERGENCYSERVICE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMERGENCYSERVICE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMERGENCYSERVICE):
    case SUCCESS(ACTION_TYPES.UPDATE_EMERGENCYSERVICE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMERGENCYSERVICE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/emergency-services';

// Actions

export const getEntities: ICrudGetAllAction<IEmergencyService> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EMERGENCYSERVICE_LIST,
    payload: axios.get<IEmergencyService>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IEmergencyService> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMERGENCYSERVICE,
    payload: axios.get<IEmergencyService>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmergencyService> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMERGENCYSERVICE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmergencyService> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMERGENCYSERVICE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmergencyService> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMERGENCYSERVICE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
