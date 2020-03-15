import React = require('react');
import i18next = require('i18next');

import {NavBar} from '../../common/NavBar';
import {NavBottom} from '../../common/NavBottom';
import {FestivalItem} from '../common/FestivalItem'
import {Page} from './Page';
import {localeStore} from '../../stores/LocaleStore'
import {quickFinderStore} from '../../stores/QuickFinderStore'


export class TravelTips extends React.Component<{}, {}> {   

	ComponentDidMount(){

	}
   componentWillUnmount() {

    }
	
    render() {
        return (
            <Page title="Travel Tips">                       
	        	<div className="row">
		            <div className="col-md-2" />              	               
		            <div className="col-md-8">								
		              	<h3 classname="text-center">Travel Tips : {(localeStore.getVisitedCountry() == null) ? 'Kenya' : <span> {localeStore.getVisitedCountry()}</span> }</h3> <br /><br />

		              	<div className="panel-group" id="kenya">
		              		<FestivalItem href="#11" id="11" title="Before You Book">
		              			<h5> <strong><i> Pre and Post Accomodiation </i></strong> </h5>
           						<p>
									When starting or ending your tour, we always advise that you arrive the day before your tour is set to depart and leave the day after your tour ends. The reason for this is twofold:
								</p>
								<p>
									Have a completely stress free time knowing that your tour or your flight is not going to leave without you as you have catered for every eventuality and are completely organised by the time you set off on your African adventure. We often have guests wanting to fly out on the evening that their tour ends and we discourage this as far as possible as it put unnecessary pressure on the entire group and that is the last thing that anyone wants at the end of a wonderful time on tour.
								</p>
                            </FestivalItem>	 

							<FestivalItem href="#22" id="22" title="Currency">
      							<p>
									In Southern and East Africa, both local currencies and US Dollars are widely accepted. The conversion rates for local currencies do however fluctuate quite regularly so if you plan on using local currencies in the countries you visit, please check the exchange rate before you depart on tour.
								</p>
								<h5><strong><i> Cash </i></strong></h5>
								<p>
									If you are carrying US dollars, always make sure that they have been printed after 2005 and if possible, request that your bank provides you with notes that are not torn or severely creased as you may find that the bills will not be accepted if they are not in a good condition.
								</p>
								<h5><strong><i> Credit Cards </i></strong></h5>
								<p>
									Most of the major Southern and East African cities do have credit card facilities available, however they may charge you a surcharge to use the facility. Visa and MasterCard are the two most widely accepted credit cards in Africa, while Diners and American Express may not always be accepted. Cards should have a chip and pin if you plan to use them to withdraw money from ATMs. Please be sure to advise your bank before you travel that you will be using your card in a different country as they may block access to your card if they do not know that you are travelling.
								</p>
								<h5><strong><i> ATM/Debit Cards </i></strong></h5>
								<p> 
									ATM cards are a good way to withdraw local currency on arrival in a new country. This may not always be possible but it is an option in most cases. Cards should have a chip and pin. The amount that you withdraw depends on how long you will be in the country for and what you plan to spend your money on while you’re there.
								</p>

								<h5><strong><i> Street Vendors </i></strong></h5>
								<p>
									You may encounter black market traders on arrival in countries who will offer to exchange your US$’s for a more favourable rate than the banks. Please don’t be tempted to do this, it is not worth the risk or the hassle and it is not good practice to display your foreign currency.
								</p>
                            </FestivalItem>

							<FestivalItem href="#33" id="33" title="What To Pack">
          						<p>
									Your luggage is limited to one backpack and one daypack weighing no more than 20kg.  As a general rule, if you cannot lift your own luggage, you’ve got too much stuff!  Most people make the mistake of bringing too many clothes on tour, less is more in this instance.
								</p>
								<p>
									Please keep in mind that this is an adventure tour in an adventure truck and luggage should not include any Samsonite style suitcases.  As the wheelie / trolley bags have a solid frame, they don’t squeeze into spaces the same way a backpack or a duffel bag does, some of the lockers are as narrow as 38cms.  If the frame does not fit these diameters, there aren’t many other places to put your bag and it will inconvenience everyone else on tour.  Also, by day two, the mud and stones will get into the little wheels on your wheelie bag and the novelty will be over and you’ll be carrying your suitcase from A to B.  They aren’t as comfortable to carry as a duffel bag and as you think you don’t have to carry it, you end up packing a lot more than you normally would.
								</p>
								<a href=""> You should pack the following </a>
                            </FestivalItem>

							<FestivalItem href="#44" id="44" title="Visas">
  								<p>
  									As visa requirements vary considerably depending on your nationality, please contact the various embassies or a visa service agencies to re-check visa requirements at least four weeks prior to departing for your tour. Please note that visas are the responsibility of the traveller and that Touranb will not be held responsible for clients being denied entry should they not be in the possession of the relevant visas.
								</p>
								<p>
									All travellers must be in possession of a valid onward/return air ticket or proof of other means of transport enabling the traveller to leave the country in which your adventure tour terminates. Alternatively you must have proof of sufficient funds (e.g. credit card) enabling you to purchase an air ticket to leave the country. Should the adventure tour you are joining be re-entering a country, be sure to have a multiple entry visa that enables you to re-enter the country.
								</p>
                            </FestivalItem>

							<FestivalItem href="#55" id="55" title="Health and Safety">
								<h5><strong><i> General Safety </i></strong></h5>
      							<p>
      								The guide has authority on tour at all times and this includes decisions regarding the safety of our guests on tour.
								</p>
								<p>
									It is important that you inform the tour operator of any medical conditions or prescription drugs that you are taking such as diabetes or asthma etc. as we are sometimes 300km or more far from the nearest medical assistance.  This is especially important if your medication has to be kept at a certain temperature, we must know about this beforehand.
								</p>
								<p>
									Please report to your tour leader immediately if you are feeling even slightly ill as they may need to make plans for you to get to medical assistance promptly.  Many travellers can feel sick within the first two weeks of travelling and this is very common and due to your body reacting to germs and bacteria it is unfamiliar with.  Keep this in mind, but do not take it lightly, keep your guides up to date with how you’re feeling.
								</p>

								<h5><strong><i> Water</i></strong></h5>
								<p>
									One of the most common ailments on tour is dehydration.  You should be drinking a minimum of 2 to 3 litres of water per day, and even more during the hot summer months.  The water on tour is not always drinkable so your guides will advise you when not to drink the local water.  Bottled mineral water is available for sale at most camp sites, keep in mind that this can be expensive.  In most places north of South Africa it is necessary for you to buy your own drinking water.  Your guides will point out where drinking water can be purchased (i.e at local shops / supermarkets).  There is a 200 litre water tank on the truck, but this is for emergency use only and generally doesn’t taste very good.
								</p>

								<p>
									Malaria is a serious problem in Africa however it does not have to be a problem for you as long as you are vigilant about using your mosquito repellent and you take your malaria tablets.  Malaria is transmitted by mosquitoes and is more prevalent where there are high concentrations of people and water.  The main points to keep in mind about malaria:
								</p>
								<ul>
										<li>
											Prevent getting bitten by wearing clothes that cover your bare skin.  Long shirts, socks, shoes and long pants after dark – most mosquito bites occur below the knee.
										</li>
										<li>
											keep your mosquito net closed and be vigilant at sunset as this is when the mosquitoes are most prolific.
										</li>
										<li>
											Use an effective prophylactic and speak to your doctor about options for antimalarial tablets.
										</li>
										<li>
											Insect repellent is the single most important line of defence.  Make sure you bring enough of an effective (preferably stick or lotion) repellent and that you use it liberally and frequently!  You need to look for the active ingredient (DEET) on the bottle.
										</li>
									</ul>
								<p>
									Malaria prophylactics do not prevent Malaria, but do treat it if you come down with the disease.  They also prevent you from getting seriously ill.  It is not true that Malaria cannot be cured.
								</p>
								<p>
									You have 2 broad malaria prophylaxis choices:
								</p>
								<p>
									Daily tablets: Doxycycline or Chloroquine & Paludrine combination pills <br />
									Weekly tablets: Larium, Meﬂiam, Meﬂoquine, Malarine
								</p>
								<p>
									Please note that the Chloriquine & Paludrine combination pills are virtually ineffective in East Africa due to their widespread usage there in the past.  We do not recommend that you take this type of prophylaxis unless you are unable to take any of the others.  You may have heard negative reports about Larium and its side effects however, it is 95% effective, while the daily tablets are only about 30% effective.  Please consult your doctor before selecting your prophylaxis.
								</p>
								<p>
									It is very important to begin taking your tablets one week before entering a malaria area, and for four weeks after leaving, as this is the incubation period.
								</p>

								<h5><strong><i> Vaccinations </i></strong></h5>
								<p>
									We recommend that you have the following vaccinations for Africa: Hepatitis A (Havrax), Cholera, Yellow Fever, Tetanus Booster Shot, Rabies.  Please note this guide is for information only – you should consult your doctor or travel clinic for the latest requirements.  If you have entered a Yellow Fever infected area, you will be required to have a vaccination before entering into other countries.
								</p>
								<p>
									If travelling further north of Vic Falls, then you will definitely require proof of a Yellow Fever vaccination.  Travel Clinics provide vaccinations and Malaria tablets, as well as free consultations.
								</p>

								<h5><strong><i> Security </i></strong></h5>
								<p>
									Travelling in any country has its potential dangers and in African countries you will find that it is no different.  Due to massive levels of poverty, petty theft is rampant.
								</p>
								<p>
				              		Basic rules apply:
				              	</p>
				              	<ul>
				              		<li>
				              			Do not bring unnecessary valuables along with you such as jewellery or expensive watches.
				              		</li>
			              			<li>
				              			 Do not leave your personal possessions unattended
				              		</li>
			              			<li>
				              			Do not be reckless in your behaviour. Be careful
				              		</li>
				              		<li>
				              			Always walk together as a group, especially at night
				              		</li>
				              	</ul>
				              	<p>
				              		It is also suggested that you photocopy and photograph all your travel documents and belongings and store them separately to the originals. Security of the vehicle is part of the reality of tour life and you will be expected to assist when necessary.
				              	</p>
                            </FestivalItem>

							<FestivalItem href="#66" id="66" title="Medical and Travel Insurance">
          						<p>
									Please note that it is a condition of booking on any Touranb that you have adequate medical insurance.  We require the details of your insurance policy for our records before you will be permitted to depart on tour so please keep these with you at departure.  The medical insurance is so important in the case of an emergency in a remote area (which is most areas we travel to) you will have to be airlifted.
				              	</p>
				              	<p>
				              		Medical insurance is usually relatively inexpensive and we recommend that you buy a separate policy in your country of origin.  If you purchase insurance in Africa please note that it will not cover you to be returned to your own country in the event of an emergency, it will only return you to the country in which you purchased the policy.
				              	</p>
                            </FestivalItem>
			            </div>

		    {/*          	{
		              		(localeStore.getVisitedCountry() == 'Ethiopia') ? 
			              	(	

			              	):
				              	(
				              		(localeStore.getVisitedCountry() == 'Kenya') ? 
			              			(	
					              		
					              	) :
					              		(
				              				(localeStore.getVisitedCountry() == 'Tanzania') ? 
					              			(	
          					              		
							              	) :
							              		(
						              				(localeStore.getVisitedCountry() == 'Uganda') ? 
							              			(	
              						              		
									              	) :
									              		(
								              				(localeStore.getVisitedCountry() == 'South Africa') ? 
									              			(	
		              						              		
											              	) :
											              		(
			              						              		
					              								)	
									              		)	

							              		)	
					              		)	
				              	)	
			            }     */}
			            <div className="vertical-space-4x"> </div>
		            </div>
		            <div className="col-md-2" />				
		        </div>
            </Page>
        );
    }
}