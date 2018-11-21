import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IArmour, defaultValue } from 'app/shared/model/armour.model';

export const ACTION_TYPES = {
  FETCH_ARMOUR_LIST: 'armour/FETCH_ARMOUR_LIST',
  FETCH_ARMOUR: 'armour/FETCH_ARMOUR',
  CREATE_ARMOUR: 'armour/CREATE_ARMOUR',
  UPDATE_ARMOUR: 'armour/UPDATE_ARMOUR',
  DELETE_ARMOUR: 'armour/DELETE_ARMOUR',
  RESET: 'armour/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArmour>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ArmourState = Readonly<typeof initialState>;

// Reducer

export default (state: ArmourState = initialState, action): ArmourState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ARMOUR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARMOUR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ARMOUR):
    case REQUEST(ACTION_TYPES.UPDATE_ARMOUR):
    case REQUEST(ACTION_TYPES.DELETE_ARMOUR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ARMOUR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARMOUR):
    case FAILURE(ACTION_TYPES.CREATE_ARMOUR):
    case FAILURE(ACTION_TYPES.UPDATE_ARMOUR):
    case FAILURE(ACTION_TYPES.DELETE_ARMOUR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARMOUR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ARMOUR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARMOUR):
    case SUCCESS(ACTION_TYPES.UPDATE_ARMOUR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARMOUR):
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

const apiUrl = 'api/armours';

// Actions

export const getEntities: ICrudGetAllAction<IArmour> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ARMOUR_LIST,
  payload: axios.get<IArmour>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IArmour> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARMOUR,
    payload: axios.get<IArmour>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IArmour> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARMOUR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IArmour> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARMOUR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArmour> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARMOUR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
