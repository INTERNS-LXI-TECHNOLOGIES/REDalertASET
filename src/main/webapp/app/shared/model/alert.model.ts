import { IEmergencyService } from 'app/shared/model/emergency-service.model';

export const enum AlertType {
  RED = 'RED',
  ORANGE = 'ORANGE',
  GREEN = 'GREEN'
}

export interface IAlert {
  id?: number;
  type?: AlertType;
  userDomainId?: number;
  locationId?: number;
  emergencyServices?: IEmergencyService[];
}

export const defaultValue: Readonly<IAlert> = {};
