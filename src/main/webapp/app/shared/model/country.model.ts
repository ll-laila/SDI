import { type IRegion } from '@/shared/model/region.model';

export interface ICountry {
  id?: number;
  countryname?: string;
  countrycode?: string;
  countryFlagcode?: string | null;
  countryFlag?: string | null;
  notes?: string | null;
  creaDate?: Date | null;
  updateDate?: Date | null;
  region?: IRegion | null;
}

export class Country implements ICountry {
  constructor(
    public id?: number,
    public countryname?: string,
    public countrycode?: string,
    public countryFlagcode?: string | null,
    public countryFlag?: string | null,
    public notes?: string | null,
    public creaDate?: Date | null,
    public updateDate?: Date | null,
    public region?: IRegion | null,
  ) {}
}
