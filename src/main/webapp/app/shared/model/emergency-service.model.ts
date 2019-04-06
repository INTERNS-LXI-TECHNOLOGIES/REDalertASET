export const enum ServiceName {
  FIRE = 'FIRE',
  AMBULANCE = 'AMBULANCE',
  POLICE = 'POLICE'
}

export interface IEmergencyService {
  id?: number;
  name?: ServiceName;
  phone?: number;
  alertId?: number;
}

export const defaultValue: Readonly<IEmergencyService> = {};
