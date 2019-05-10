import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceAuthority, defaultValue } from 'app/shared/model/service-authority.model';

export const ACTION_TYPES = {
  FETCH_SERVICEAUTHORITY_LIST: 'serviceAuthority/FETCH_SERVICEAUTHORITY_LIST',
  FETCH_SERVICEAUTHORITY: 'serviceAuthority/FETCH_SERVICEAUTHORITY',
  CREATE_SERVICEAUTHORITY: 'serviceAuthority/CREATE_SERVICEAUTHORITY',
  UPDATE_SERVICEAUTHORITY: 'serviceAuthority/UPDATE_SERVICEAUTHORITY',
  DELETE_SERVICEAUTHORITY: 'serviceAuthority/DELETE_SERVICEAUTHORITY',
  RESET: 'serviceAuthority/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceAuthority>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ServiceAuthorityState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceAuthorityState = initialState, action): ServiceAuthorityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEAUTHORITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEAUTHORITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEAUTHORITY):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEAUTHORITY):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEAUTHORITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEAUTHORITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEAUTHORITY):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEAUTHORITY):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEAUTHORITY):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEAUTHORITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEAUTHORITY_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEAUTHORITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEAUTHORITY):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEAUTHORITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEAUTHORITY):
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

const apiUrl = 'api/service-authorities';

// Actions

export const getEntities: ICrudGetAllAction<IServiceAuthority> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEAUTHORITY_LIST,
    payload: axios.get<IServiceAuthority>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IServiceAuthority> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEAUTHORITY,
    payload: axios.get<IServiceAuthority>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IServiceAuthority> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEAUTHORITY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceAuthority> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEAUTHORITY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceAuthority> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEAUTHORITY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
