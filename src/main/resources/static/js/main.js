(async () => {
    async function fetchData(exchange) {
        const now = Math.floor(new Date().getTime());
        const response = await axios.get(`/chart/area`, {
            params: {
                cex: exchange,
                symbol: 'USDT',
                interval: 900,
                limit: 1000,
                to: now
            }
        });
        return response.data;
    }

    async function updateChart(exchange) {
        const data = await fetchData(exchange);
        Highcharts.setOptions({
            lang: {
                rangeSelectorZoom: ''
            },
            chart: {
                backgroundColor: '#f4f4f4',
                zooming: {
                    resetButton: {
                        theme: {
                            fill: '#f23644',
                            stroke: 'none',
                            style: {
                                color: '#ffffff'
                            },
                            r: 8,
                            states: {
                                hover: {
                                    style: {
                                        color: '#000000'
                                    }
                                }
                            }
                        }
                    }
                }
            },
            navigator: {
                enabled: false
            },
            exporting: {
                buttons: {
                    contextButton: {
                        enabled: false,
                    },
                },
            },
            credits: {
                enabled: false
            },
            xAxis: {
                labels: {
                    style: {
                        color: '#f4f4f4'
                    }
                }
            },
            yAxis: {
                labels: {
                    style: {
                        color: '#3c3c3c'
                    }
                }
            },
            navigator: {
                series: {
                    fillColor: {
                        linearGradient: [0, 0, 0, 40],
                        stops: [
                            [0, '#f4f4f4'],
                            [1, '#f4f4f4']
                        ]
                    }
                },
                xAxis: {
                    labels: {
                        style: {
                            color: '#ffffff',
                            opacity: 1,
                            textOutline: '#f4f4f4'
                        }
                    },
                },
                maskFill: '#f4f4f4',
                handles: {
                    backgroundColor: '#f4f4f4',
                    borderRadius: '50%',
                    width: 20,
                    height: 20
                }
            },
            scrollbar: {
                barBackgroundColor: '#f4f4f4',
                trackBorderColor: '#f4f4f4'
            },
            rangeSelector: {
                buttonTheme: {
                    fill: 'none',
                    stroke: 'none',
                    'stroke-width': 0,
                    r: 8,
                    style: {
                        color: '#d9d7d7',
                        fontWeight: 'bold',
                        fontSize: '1em'
                    },
                    states: {
                        select: {
                            fill: '#f23644',
                            style: {
                                color: '#ffffff'
                            }
                        },
                        hover: {
                            fill: '#f23644',
                            style: {
                                color: '#f4f4f4'
                            }
                        }
                    }
                }
            },
            plotOptions: {
                area: {
                    threshold: null,
                    color: '#f23644',
                    fillColor: {
                        linearGradient: [0, 0, 0, 450],
                        stops: [
                            [0, '#f4f4f4'],
                            [1, '#f4f4f4']
                        ]
                    },
                }
            },
            tooltip: {
                backgroundColor: '#212020',
                style: {
                    color: '#ffffff'
                }
            }
        });
        Highcharts.stockChart('klines', {
            xAxis: {
                tickInterval: 900 * 1000,
                tickLength: 0,
                lineWidth: 0,
                crosshair: {
                    width: 1,
                    color: '#f4f4f4',
                    zIndex: 3
                }
            },
            scrollbar: {
                barBorderRadius: 4,
                height: 8,
                margin: 0,
                trackBorderRadius: 4
            },
            yAxis: {
                tickInterval: 10,
                gridLineWidth: 0,
                offset: 30,
            },
            navigator: {
                enabled: false
            },
            rangeSelector: {
                enabled: false,
            },
            plotOptions: {
                series: {
                    pointInterval: 900 * 1000,
                }
            },
            series: [{
                data: data,
                type: 'area',
                tooltip: {
                    valueDecimals: 4,
                    pointFormat: '{point.y}'
                },
            }],
            tooltip: {
                shape: 'rect',
                shadow: false,
                borderWidth: 0
            }
        });

        document.getElementById('dropdown-selected').innerText = exchange;

    }

    document.addEventListener('DOMContentLoaded', (event) => {
        const initialExchange = document.getElementById('dropdown-selected').innerText;
        updateChart(initialExchange);
    });

    document.querySelector('.dropdown-menu').addEventListener('click', function (event) {
        const target = event.target;
        if (target.classList.contains('dropdown-item')) {
            const exchange = target.getAttribute('data-value');
            document.getElementById('dropdown-selected').innerText = exchange;
            updateChart(exchange);

            document.querySelectorAll('.dropdown-item').forEach(item => {
                item.classList.remove('active');
            });
            target.classList.add('active');
        }
    });
})();
